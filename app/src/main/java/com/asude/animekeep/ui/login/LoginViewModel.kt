package com.asude.animekeep.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess = _loginSuccess.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val auth: FirebaseAuth = Firebase.auth

    fun signInWithGoogle(credential: AuthCredential) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                auth.signInWithCredential(credential).await()
                _loginSuccess.value = true
            } catch (e: Exception) {
                _errorMessage.value = "Google Sign-In Failed: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            if (email.isBlank() || pass.isBlank()) {
                _errorMessage.value = "Please fill in all fields."
                _isLoading.value = false
                return@launch
            }

            try {
                auth.signInWithEmailAndPassword(email, pass).await()
                _loginSuccess.value = true
            } catch (e: Exception) {
                _errorMessage.value = "Login failed: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun register(username: String, email: String, pass: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            if (username.isBlank() || email.isBlank() || pass.isBlank()) {
                _errorMessage.value = "Please fill all fields."
                _isLoading.value = false
                return@launch
            }

            try {
                val result = auth.createUserWithEmailAndPassword(email, pass).await()
                val user = result.user

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()

                user?.updateProfile(profileUpdates)?.await()

                _loginSuccess.value = true

            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Registration failed."
                _isLoading.value = false
            }
        }
    }
}
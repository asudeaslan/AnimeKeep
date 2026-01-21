package com.asude.animekeep.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asude.animekeep.data.repository.AnimeRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val email: String = "Guest",
    val watchingCount: Int = 0,
    val plannedCount: Int = 0,
    val completedCount: Int = 0
)

class ProfileViewModel(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUserProfile()
        loadStatistics()
    }

    private fun loadUserProfile() {
        val user = FirebaseAuth.getInstance().currentUser
        val savedName = user?.displayName

        val finalName = if (!savedName.isNullOrBlank()) {
            savedName
        } else {
            user?.email?.substringBefore("@")?.replaceFirstChar { it.uppercase() } ?: "Guest"
        }

        _uiState.update { it.copy(email = finalName) }
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            launch {
                repository.getAnimeByStatus("Watching").collectLatest { list ->
                    _uiState.update { it.copy(watchingCount = list.size) }
                }
            }
            launch {
                repository.getAnimeByStatus("Plan to Watch").collectLatest { list ->
                    _uiState.update { it.copy(plannedCount = list.size) }
                }
            }
            launch {
                repository.getAnimeByStatus("Completed").collectLatest { list ->
                    _uiState.update { it.copy(completedCount = list.size) }
                }
            }
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}
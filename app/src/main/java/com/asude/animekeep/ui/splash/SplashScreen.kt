package com.asude.animekeep.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        if (FirebaseAuth.getInstance().currentUser != null) {
            onNavigateToHome()
        } else {
            onNavigateToLogin()
        }
    }
}
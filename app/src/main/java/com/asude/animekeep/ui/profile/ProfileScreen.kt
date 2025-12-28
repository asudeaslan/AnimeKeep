package com.asude.animekeep.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.asude.animekeep.ui.AppViewModelProvider

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var showSettingsDialog by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Cyan)
                .padding(16.dp),
            tint = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = uiState.email,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Otaku Level: ${uiState.completedCount * 5}",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard(count = uiState.watchingCount.toString(), label = "Watching")
            StatCard(count = uiState.plannedCount.toString(), label = "Planned")
            StatCard(count = uiState.completedCount.toString(), label = "Completed")
        }

        Spacer(modifier = Modifier.height(40.dp))

        ProfileMenuItem(icon = Icons.Default.Settings, text = "Settings") {
            showSettingsDialog = true
        }
        ProfileMenuItem(icon = Icons.Default.Notifications, text = "Notifications") {
            showNotificationsDialog = true
        }
        ProfileMenuItem(icon = Icons.Default.Info, text = "Help & Support") {
            showHelpDialog = true
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.logout()
                onLogout()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Logout", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }

    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            confirmButton = { TextButton(onClick = { showSettingsDialog = false }) { Text("OK") } },
            title = { Text("Settings") },
            text = { Text("App Version: 1.0.0\nTheme: Dark Mode (Default)\nLanguage: English") }
        )
    }

    if (showNotificationsDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationsDialog = false },
            confirmButton = { TextButton(onClick = { showNotificationsDialog = false }) { Text("Close") } },
            title = { Text("Notifications") },
            text = { Text("Notifications are currently enabled for:\n- New Episodes\n- List Updates") }
        )
    }

    if (showHelpDialog) {
        AlertDialog(
            onDismissRequest = { showHelpDialog = false },
            confirmButton = { TextButton(onClick = { showHelpDialog = false }) { Text("Got it") } },
            title = { Text("Help & Support") },
            text = { Text("For support, please contact:\nsupport@animekeep.com\n\nDeveloped by Asude Aslan.") }
        )
    }
}

@Composable
fun StatCard(count: String, label: String) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .background(Color(0xFF303030), RoundedCornerShape(12.dp))
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = count, color = Color.Cyan, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = label, color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.LightGray)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = ">", color = Color.Gray)
    }
}
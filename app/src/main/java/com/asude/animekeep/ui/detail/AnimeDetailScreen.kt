package com.asude.animekeep.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.asude.animekeep.model.Anime
import com.asude.animekeep.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailScreen(
    anime: Anime,
    onNavigateUp: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var showStatusDialog by remember { mutableStateOf(false) }

    val buttonText = if (!anime.userStatus.isNullOrEmpty() && anime.userStatus != "NONE") {
        anime.userStatus
    } else {
        "Add to List"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(anime.title ?: "Anime Detail") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showStatusDialog = true },
                icon = { Icon(Icons.Filled.Edit, "Edit") },
                text = { Text(buttonText ?: "Add to List") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(anime.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = anime.title ?: "Unknown Title",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            SuggestionChip(
                onClick = { },
                label = { Text("Score: ${anime.score ?: "??"}") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Synopsis", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = anime.synopsis ?: "No synopsis available.",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (showStatusDialog) {
            AlertDialog(
                onDismissRequest = { showStatusDialog = false },
                title = { Text("Update Status") },
                text = {
                    Column {
                        StatusOption(text = "Watching") {
                            viewModel.saveAnime(anime, "Watching")
                            showStatusDialog = false
                        }
                        StatusOption(text = "Plan to Watch") {
                            viewModel.saveAnime(anime, "Plan to Watch")
                            showStatusDialog = false
                        }
                        StatusOption(text = "Completed") {
                            viewModel.saveAnime(anime, "Completed")
                            showStatusDialog = false
                        }
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        StatusOption(text = "Remove from List", color = MaterialTheme.colorScheme.error) {
                            viewModel.removeAnime(anime)
                            showStatusDialog = false
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showStatusDialog = false }) { Text("Cancel") }
                }
            )
        }
    }
}

@Composable
fun StatusOption(
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text(text, color = color, modifier = Modifier.fillMaxWidth())
    }
}
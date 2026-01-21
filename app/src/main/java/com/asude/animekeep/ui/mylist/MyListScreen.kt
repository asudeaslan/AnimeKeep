package com.asude.animekeep.ui.mylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.asude.animekeep.model.Anime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyListScreen(
    uiState: MyListUiState,
    onTabSelected: (Int) -> Unit,
    onAnimeClick: (Anime) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(title = { Text("My Lists") })

                TabRow(selectedTabIndex = uiState.selectedTab) {
                    Tab(
                        selected = uiState.selectedTab == 0,
                        onClick = { onTabSelected(0) },
                        text = { Text("Watching") }
                    )
                    Tab(
                        selected = uiState.selectedTab == 1,
                        onClick = { onTabSelected(1) },
                        text = { Text("Planned") }
                    )
                    Tab(
                        selected = uiState.selectedTab == 2,
                        onClick = { onTabSelected(2) },
                        text = { Text("Completed") }
                    )
                }
            }
        }
    ) { innerPadding ->
        if (uiState.animeList.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Your list is empty.")
            }
        } else {
            LazyColumn(contentPadding = innerPadding, modifier = modifier) {
                items(uiState.animeList) { anime ->
                    AnimeListItem(anime = anime, onClick = { onAnimeClick(anime) })
                }
            }
        }
    }
}

@Composable
fun AnimeListItem(anime: Anime, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(anime.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(70.dp)
                    .fillMaxHeight()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = anime.title ?: "Unknown",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = "Status: ${anime.userStatus}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
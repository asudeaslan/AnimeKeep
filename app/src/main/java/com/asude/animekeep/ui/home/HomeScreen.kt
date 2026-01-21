package com.asude.animekeep.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.asude.animekeep.model.Anime
import com.asude.animekeep.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onAnimeClick: (Anime) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF303030))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Discover",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                viewModel.searchAnime(it)
            },
            placeholder = { Text("Search Anime...", color = Color.Gray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = {
                        searchText = ""
                        viewModel.resetSearch()
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF424242),
                unfocusedContainerColor = Color(0xFF424242),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.Cyan,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Cyan)
            }
        } else {
            if (uiState.isSearching) {
                Text("Search Results", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                uiState.searchResults.forEach { anime ->
                    Row(modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth().clickable { onAnimeClick(anime) }) {
                        AnimeCard(anime, onClick = { onAnimeClick(anime) })
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = anime.title ?: "Unknown Title",
                            color = Color.White,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }

            } else {
                AnimeSection(title = "Trending Now", animeList = uiState.trendingList, onAnimeClick = onAnimeClick)
                Spacer(modifier = Modifier.height(16.dp))

                AnimeSection(title = "Upcoming Season", animeList = uiState.upcomingList, onAnimeClick = onAnimeClick)
                Spacer(modifier = Modifier.height(16.dp))

                AnimeSection(title = "All Time Popular", animeList = uiState.popularList, onAnimeClick = onAnimeClick)
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun AnimeSection(title: String, animeList: List<Anime>, onAnimeClick: (Anime) -> Unit) {
    Column {
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(bottom = 8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(animeList) { anime -> AnimeCard(anime, onClick = { onAnimeClick(anime) }) }
        }
    }
}

@Composable
fun AnimeCard(anime: Anime, onClick: () -> Unit = {}) {
    Column(modifier = Modifier.width(120.dp).clickable { onClick() }) {
        AsyncImage(
            model = anime.imageUrl,
            contentDescription = anime.title ?: "Anime Image",
            modifier = Modifier.height(160.dp).fillMaxWidth().clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = anime.title ?: "Unknown",
            color = Color.White,
            fontSize = 14.sp,
            maxLines = 1
        )
    }
}
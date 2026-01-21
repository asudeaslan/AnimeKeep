package com.asude.animekeep.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asude.animekeep.data.repository.AnimeRepository
import com.asude.animekeep.model.Anime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val trendingList: List<Anime> = emptyList(),
    val upcomingList: List<Anime> = emptyList(),
    val popularList: List<Anime> = emptyList(),
    val searchResults: List<Anime> = emptyList(),
    val isSearching: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class HomeViewModel(private val repository: AnimeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val trending = repository.getTopAnime()
                val upcoming = repository.getUpcomingAnime()
                val popular = repository.getPopularAnime()

                _uiState.update {
                    it.copy(
                        trendingList = trending,
                        upcomingList = upcoming,
                        popularList = popular,
                        isLoading = false
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Failed to load data")
                }
            }
        }
    }

    fun searchAnime(query: String) {
        if (query.isBlank()) {
            resetSearch()
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isSearching = true) }
            try {
                val results = repository.searchAnime(query)
                _uiState.update {
                    it.copy(
                        searchResults = results,
                        isLoading = false
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Search failed")
                }
            }
        }
    }

    fun resetSearch() {
        _uiState.update { it.copy(isSearching = false, searchResults = emptyList()) }
    }
}
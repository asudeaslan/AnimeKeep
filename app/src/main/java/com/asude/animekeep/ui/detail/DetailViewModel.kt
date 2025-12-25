package com.asude.animekeep.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asude.animekeep.data.repository.AnimeRepository
import com.asude.animekeep.model.Anime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailUiState(
    val anime: Anime? = null,
    val isLoading: Boolean = false
)

class DetailViewModel(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    fun getAnimeDetails(animeId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val anime = repository.getAnimeDetail(animeId)
            _uiState.update {
                it.copy(anime = anime, isLoading = false)
            }
        }
    }

    fun saveAnime(anime: Anime, status: String) {
        viewModelScope.launch {
            val updatedAnime = anime.copy(userStatus = status)
            repository.insertAnime(updatedAnime)
            _uiState.update { it.copy(anime = updatedAnime) }
        }
    }

    fun removeAnime(anime: Anime) {
        viewModelScope.launch {
            repository.deleteAnime(anime)
            val resetAnime = anime.copy(userStatus = "NONE")
            _uiState.update { it.copy(anime = resetAnime) }
        }
    }
}
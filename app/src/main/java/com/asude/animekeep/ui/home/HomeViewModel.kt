package com.asude.animekeep.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asude.animekeep.domain.model.Anime
import com.asude.animekeep.domain.usecase.GetTopAnimeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getTopAnimeUseCase: GetTopAnimeUseCase
) : ViewModel() {

    private val _topAnime = MutableStateFlow<List<Anime>>(emptyList())
    val topAnime: StateFlow<List<Anime>> = _topAnime

    fun loadTopAnime() {
        viewModelScope.launch {
            _topAnime.value = getTopAnimeUseCase()
        }
    }
}

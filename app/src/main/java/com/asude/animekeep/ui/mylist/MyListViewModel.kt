package com.asude.animekeep.ui.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asude.animekeep.data.repository.AnimeRepository
import com.asude.animekeep.model.Anime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

data class MyListUiState(
    val animeList: List<Anime> = emptyList(),
    val selectedTab: Int = 0
)

class MyListViewModel(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private val _selectedTab = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<MyListUiState> = _selectedTab
        .flatMapLatest { tabIndex ->
            val status = when (tabIndex) {
                0 -> "Watching"
                1 -> "Plan to Watch"
                2 -> "Completed"
                else -> "Watching"
            }
            animeRepository.getAnimeByStatus(status)
        }
        .map { list ->
            MyListUiState(animeList = list, selectedTab = _selectedTab.value)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MyListUiState()
        )

    fun onTabSelected(index: Int) {
        _selectedTab.value = index
    }
}
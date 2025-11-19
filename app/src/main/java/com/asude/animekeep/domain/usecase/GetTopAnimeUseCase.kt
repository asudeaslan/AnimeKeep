package com.asude.animekeep.domain.usecase

import com.asude.animekeep.domain.model.Anime
import com.asude.animekeep.domain.repository.AnimeRepository

class GetTopAnimeUseCase(
    private val repo: AnimeRepository
) {
    suspend operator fun invoke(): List<Anime> = repo.getTopAnime()
}

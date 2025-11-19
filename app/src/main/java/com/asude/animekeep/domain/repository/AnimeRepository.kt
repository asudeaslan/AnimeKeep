package com.asude.animekeep.domain.repository

import com.asude.animekeep.domain.model.Anime

interface IAnimeRepository {
    suspend fun getTopAnime(): List<Anime>
    suspend fun getUpcomingAnime(): List<Anime>
    suspend fun searchAnime(query: String): List<Anime>
    suspend fun getAnimeDetails(id: Int): Anime
}

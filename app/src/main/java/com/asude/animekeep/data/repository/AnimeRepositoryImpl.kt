package com.asude.animekeep.data.repository

import com.asude.animekeep.data.remote.JikanApi
import com.asude.animekeep.domain.model.Anime
import com.asude.animekeep.domain.repository.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeRepositoryImpl(
    private val api: JikanApi
) : AnimeRepository {

    private fun mapToDomain(j: com.asude.animekeep.data.model.jikan.JikanAnime): Anime {
        return Anime(
            id = j.mal_id,
            title = j.title,
            imageUrl = j.images?.jpg?.image_url,
            synopsis = j.synopsis,
            genres = j.genres?.mapNotNull { it.name } ?: emptyList(),
            status = j.status
        )
    }

    override suspend fun getTopAnime(): List<Anime> = withContext(Dispatchers.IO) {
        api.getTopAnime().data.map { mapToDomain(it) }
    }

    override suspend fun getUpcomingAnime(): List<Anime> = withContext(Dispatchers.IO) {
        api.getUpcomingAnime().data.map { mapToDomain(it) }
    }

    override suspend fun searchAnime(query: String): List<Anime> = withContext(Dispatchers.IO) {
        api.searchAnime(query).data.map { mapToDomain(it) }
    }

    override suspend fun getAnimeDetails(id: Int): Anime = withContext(Dispatchers.IO) {
        mapToDomain(api.getAnimeDetails(id).data)
    }
}

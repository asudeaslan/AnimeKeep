package com.asude.animekeep.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApi {


    @GET("top/anime")
    suspend fun getTopAnime(): JikanResponse

    @GET("seasons/upcoming")
    suspend fun getUpcomingAnime(): JikanResponse

    @GET("top/anime")
    suspend fun getPopularAnime(@Query("filter") filter: String = "bypopularity"): JikanResponse

    @GET("anime")
    suspend fun searchAnime(@Query("q") query: String): JikanResponse

    @GET("anime/{id}")
    suspend fun getAnimeDetails(@Path("id") id: Int): JikanDetailResponse
}


data class JikanResponse(val data: List<AnimeDto>)
data class JikanDetailResponse(val data: AnimeDto)

data class AnimeDto(
    val mal_id: Int,
    val title: String,
    val images: ImageWrapper,
    val synopsis: String?,
    val score: Double?,
    val episodes: Int?
)

data class ImageWrapper(val jpg: JpgUrl)
data class JpgUrl(val image_url: String)
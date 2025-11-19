package com.asude.animekeep.data.remote

import com.asude.animekeep.data.model.jikan.JikanAnime
import com.asude.animekeep.data.model.jikan.JikanResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApi {
    @GET("top/anime")
    suspend fun getTopAnime(): JikanResponse<List<JikanAnime>>

    @GET("seasons/upcoming")
    suspend fun getUpcomingAnime(): JikanResponse<List<JikanAnime>>

    @GET("anime")
    suspend fun searchAnime(@Query("q") query: String): JikanResponse<List<JikanAnime>>

    @GET("anime/{id}")
    suspend fun getAnimeDetails(@Path("id") id: Int): JikanResponse<JikanAnime>
}

package com.asude.animekeep.data

import android.content.Context
import com.asude.animekeep.data.remote.JikanApi
import com.asude.animekeep.data.repository.AnimeRepository
import com.asude.animekeep.data.repository.NetworkAnimeRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val animeRepository: AnimeRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

    private val baseUrl = "https://api.jikan.moe/v4/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: JikanApi by lazy {
        retrofit.create(JikanApi::class.java)
    }

    override val animeRepository: AnimeRepository by lazy {
        NetworkAnimeRepository(retrofitService)
    }
}
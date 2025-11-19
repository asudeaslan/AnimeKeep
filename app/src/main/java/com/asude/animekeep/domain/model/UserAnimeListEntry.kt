package com.asude.animekeep.domain.model

data class UserAnimeListEntry(
    val animeId: Int = 0,
    val title: String = "",
    val posterUrl: String = "",
    val status: String = "Watching",
    val timestamp: Long = System.currentTimeMillis()
)

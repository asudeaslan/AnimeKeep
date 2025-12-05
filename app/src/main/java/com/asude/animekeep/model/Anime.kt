package com.asude.animekeep.model

data class Anime(
    val id: Int = 0,
    val title: String? = "",
    val imageUrl: String? = "",
    val synopsis: String? = "",
    val score: Double? = 0.0,
    val episodes: Int? = 0,
    var userStatus: String = "NONE",
    val userId: String = ""
)
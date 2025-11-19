package com.asude.animekeep.domain.model

data class Anime(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val synopsis: String?,
    val genres: List<String>,
    val status: String?
)

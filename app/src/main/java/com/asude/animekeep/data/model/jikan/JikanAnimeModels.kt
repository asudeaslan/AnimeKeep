package com.asude.animekeep.data.model.jikan

data class JikanAnime(
    val mal_id: Int,
    val title: String,
    val images: AnimeImages? = null,
    val synopsis: String? = null,
    val genres: List<JikanGenre>? = null,
    val status: String? = null
)

data class AnimeImages(val jpg: ImageJpg? = null)
data class ImageJpg(val image_url: String? = null)
data class JikanGenre(val name: String? = null)

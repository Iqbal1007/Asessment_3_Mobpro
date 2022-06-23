package com.muhammadiqbal.favanimeapp.data.models

data class DataAnime(
    val rating: String,
    val title: String,
    val score: Double,
    val rank: Int,
    val episodes: Int,
    val mal_id: Int,
    val synopsis: String? = null,
    val images: Images,
    val genres: List<GenresItem>,
)

data class Images(
    val jpg: Jpg,
)

data class Jpg(
    val large_image_url: String? = null,
    val small_image_url: String? = null,
    val image_url: String? = null
)

data class GenresItem(
    val name: String,
    val malId: Int,
    val type: String,
    val url: String
)
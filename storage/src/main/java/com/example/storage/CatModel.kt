package com.example.storage

import kotlinx.serialization.Serializable

@Serializable
data class CatModel(
    val id: String,
    val url: String,
    val isFavorite: Boolean
)
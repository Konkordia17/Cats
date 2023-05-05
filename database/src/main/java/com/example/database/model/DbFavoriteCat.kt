package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbFavoriteCat(
    @PrimaryKey
    val id: String,
    val url: String,
    val isFavorite: Boolean
)
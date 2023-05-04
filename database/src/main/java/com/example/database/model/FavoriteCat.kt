package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteCat(
    @PrimaryKey
    val id: String,
    val url: String
)
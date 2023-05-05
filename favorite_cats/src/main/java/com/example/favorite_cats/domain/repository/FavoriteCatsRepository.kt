package com.example.favorite_cats.domain.repository

import com.example.favorite_cats.FavoriteCat

interface FavoriteCatsRepository {

    suspend fun getCatsFromDb(): List<FavoriteCat>

    suspend fun deleteCatFromDb(cat: FavoriteCat)

    suspend fun deleteAllFavoriteCats()
}

package com.example.favorite_cats.domain.use_cases

import com.example.favorite_cats.FavoriteCat
import com.example.favorite_cats.domain.repository.FavoriteCatsRepository
import javax.inject.Inject

class DeleteFavoriteCatFromDbUseCase @Inject constructor(private val repository: FavoriteCatsRepository) {

    suspend fun deleteCatFromDb(cat: FavoriteCat) {
        repository.deleteCatFromDb(cat)
    }
}
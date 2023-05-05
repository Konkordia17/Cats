package com.example.favorite_cats.domain.use_cases

import com.example.favorite_cats.domain.repository.FavoriteCatsRepository
import javax.inject.Inject

class DeleteAllFavoriteCatsUseCase @Inject constructor(private val repository: FavoriteCatsRepository) {

    suspend fun deleteAllFavoriteCats() {
        repository.deleteAllFavoriteCats()
    }
}
package com.example.cats_list.domain.use_cases

import com.example.cats_list.Cat
import com.example.cats_list.domain.repository.CatsRepository
import javax.inject.Inject

class SetCatToFavoritesUseCase @Inject constructor(private val repository: CatsRepository) {

    suspend fun setCatToFavorites(cat: Cat) {
        repository.setCatToDb(cat)
    }
}
package com.example.cats_list.domain.use_cases

import com.example.cats_list.Cat
import com.example.cats_list.domain.repository.CatsRepository
import javax.inject.Inject

class DeleteCatFromDbUseCase @Inject constructor(private val repository: CatsRepository) {

    suspend fun deleteCatFromFavorites(cat: Cat) {
        repository.deleteCatFromDb(cat)
    }

}
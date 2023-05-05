package com.example.cats_list.domain.use_cases

import com.example.cats_list.Cat
import com.example.cats_list.domain.repository.CatsRepository
import javax.inject.Inject

class GetCatsFromDbUseCase @Inject constructor(private val repository: CatsRepository) {

    suspend fun getFavoriteCats(): List<Cat> {
        return repository.getCatsFromDb()
    }

}
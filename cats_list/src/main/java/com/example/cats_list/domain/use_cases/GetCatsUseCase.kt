package com.example.cats_list.domain.use_cases

import com.example.cats_list.Cat
import com.example.cats_list.domain.repository.CatsRepository
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(private val repository: CatsRepository) {

   suspend fun getCats(page: Int): List<Cat> {
       return repository.getCats(page)
   }
}
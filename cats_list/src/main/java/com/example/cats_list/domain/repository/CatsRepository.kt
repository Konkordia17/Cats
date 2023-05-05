package com.example.cats_list.domain.repository

import com.example.cats_list.Cat

interface CatsRepository {

   suspend fun getCats(limit: Int): List<Cat>

   suspend fun setCatToDb(cat: Cat)

   suspend fun getCatsFromDb(): List<Cat>

   suspend fun deleteCatFromDb(cat: Cat)
}
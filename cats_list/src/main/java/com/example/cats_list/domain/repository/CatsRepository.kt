package com.example.cats_list.domain.repository

import com.example.cats_list.Cat

interface CatsRepository {

   suspend fun getCats(limit: Int): List<Cat>

   suspend fun setCatToDb(cat: Cat)
}
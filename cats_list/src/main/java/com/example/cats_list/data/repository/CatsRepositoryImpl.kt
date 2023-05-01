package com.example.cats_list.data.repository

import com.example.cats_list.Cat
import com.example.cats_list.domain.repository.CatsRepository
import com.example.storage.CatModel
import com.example.storage.data.CatsApi
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(private val api: CatsApi) : CatsRepository {

    override suspend fun getCats(page: Int): List<Cat> {
        return api.getCatsByPage(page).map {
            mapCatsModelToCat(it)
        }
    }

    private fun mapCatsModelToCat(catModel: CatModel): Cat {
        return Cat(
            id = catModel.id,
            url = catModel.url
        )
    }
}
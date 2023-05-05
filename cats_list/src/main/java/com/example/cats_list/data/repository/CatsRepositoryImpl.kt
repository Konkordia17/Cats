package com.example.cats_list.data.repository

import com.example.cats_list.Cat
import com.example.cats_list.domain.repository.CatsRepository
import com.example.database.FavoriteCatsDatabase
import com.example.database.model.DbFavoriteCat
import com.example.storage.CatModel
import com.example.storage.data.CatsApi
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val api: CatsApi,
    private val db: FavoriteCatsDatabase
) : CatsRepository {

    override suspend fun getCats(limit: Int): List<Cat> {
        return api.getCatsByPage(limit).map {
            mapCatsModelToCat(it)
        }
    }

    override suspend fun setCatToDb(cat: Cat) {
        db.catsDao().insertCat(cat = mapCatToFavoriteCat(cat))
    }

    override suspend fun getCatsFromDb(): List<Cat> {
        return db.catsDao().getFavoriteCats()
            .map {
                mapFavoriteCatToCat(it)
            }
    }

    override suspend fun deleteCatFromDb(cat: Cat) {
        db.catsDao().deleteFavoriteCat(cat = mapCatToFavoriteCat(cat))
    }

    private fun mapCatsModelToCat(catModel: CatModel): Cat {
        return Cat(
            id = catModel.id,
            url = catModel.url,
            isFavorite = catModel.isFavorite
        )
    }

    private fun mapCatToFavoriteCat(cat: Cat): DbFavoriteCat {
        return DbFavoriteCat(id = cat.id, url = cat.url, isFavorite = cat.isFavorite)
    }

    private fun mapFavoriteCatToCat(favoriteCat: DbFavoriteCat): Cat {
        return Cat(
            id = favoriteCat.id,
            url = favoriteCat.url,
            isFavorite = favoriteCat.isFavorite
        )
    }
}
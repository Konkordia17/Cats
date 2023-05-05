package com.example.favorite_cats.data.repository

import com.example.database.FavoriteCatsDatabase
import com.example.database.model.DbFavoriteCat
import com.example.favorite_cats.FavoriteCat
import com.example.favorite_cats.domain.repository.FavoriteCatsRepository
import javax.inject.Inject

class FavoriteCatsRepositoryImpl @Inject constructor(private val db: FavoriteCatsDatabase) :
    FavoriteCatsRepository {
    override suspend fun getCatsFromDb(): List<FavoriteCat> {
        return db.catsDao().getFavoriteCats().map {
            mapDbFavoriteCatToFavoriteCat(it)
        }
    }

    override suspend fun deleteCatFromDb(cat: FavoriteCat) {
        db.catsDao().deleteFavoriteCat(mapFavoriteCatToDbFavoriteCat(cat))
    }

    override suspend fun deleteAllFavoriteCats() {
        db.catsDao().deleteAllFavoriteCats()
    }

    private fun mapDbFavoriteCatToFavoriteCat(dbFavoriteCat: DbFavoriteCat): FavoriteCat {
        return FavoriteCat(
            id = dbFavoriteCat.id,
            url = dbFavoriteCat.url,
            isFavorite = dbFavoriteCat.isFavorite
        )
    }

    private fun mapFavoriteCatToDbFavoriteCat(favoriteCat: FavoriteCat): DbFavoriteCat {
        return DbFavoriteCat(
            id = favoriteCat.id,
            url = favoriteCat.url,
            isFavorite = favoriteCat.isFavorite
        )
    }
}
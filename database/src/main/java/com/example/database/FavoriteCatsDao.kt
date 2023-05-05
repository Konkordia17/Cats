package com.example.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.model.DbFavoriteCat

@Dao
interface FavoriteCatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCat(cat: DbFavoriteCat)

    @Query("SELECT * FROM dbFavoriteCat")
    fun getFavoriteCats(): List<DbFavoriteCat>

    @Delete
    fun deleteFavoriteCat(cat: DbFavoriteCat)

    @Query("DELETE FROM dbFavoriteCat")
    fun deleteAllFavoriteCats()
}
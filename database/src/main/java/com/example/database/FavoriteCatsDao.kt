package com.example.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.model.FavoriteCat

@Dao
interface FavoriteCatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCat(cat: FavoriteCat)

    @Query("SELECT * FROM favoriteCat")
    fun getFavoriteCats(): List<FavoriteCat>

    @Delete
    fun deleteFavoriteCat(cat: FavoriteCat)
}
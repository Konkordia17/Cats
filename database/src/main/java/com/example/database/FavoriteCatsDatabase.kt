package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.FavoriteCatsDatabase.Companion.DB_VERSION
import com.example.database.model.DbFavoriteCat

@Database(entities = [DbFavoriteCat::class], version = DB_VERSION, exportSchema = false)
abstract class FavoriteCatsDatabase() : RoomDatabase() {

    abstract fun catsDao(): FavoriteCatsDao

    companion object {
        const val DB_VERSION = 1

        @Volatile
        private var INSTANCE: FavoriteCatsDatabase? = null

        fun getInstance(context: Context): FavoriteCatsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FavoriteCatsDatabase::class.java, "FavoriteCatsDb"
            ).fallbackToDestructiveMigration().build()
    }
}
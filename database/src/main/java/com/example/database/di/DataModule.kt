package com.example.database.di

import android.content.Context
import com.example.database.FavoriteCatsDatabase
import dagger.Module
import dagger.Provides

@Module
class DataModule(private val context: Context) {

    @Provides
    fun provideDataBase(context: Context): FavoriteCatsDatabase {
        return FavoriteCatsDatabase.getInstance(context)
    }
    @Provides
    fun provideContext(): Context = context
}
package com.example.database.di

import com.example.database.Database
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideDataBase(): Database {
        return Database()
    }
}
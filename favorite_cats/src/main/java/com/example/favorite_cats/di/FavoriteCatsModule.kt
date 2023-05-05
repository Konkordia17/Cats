package com.example.favorite_cats.di

import com.example.database.FavoriteCatsDatabase
import com.example.favorite_cats.data.repository.FavoriteCatsRepositoryImpl
import com.example.favorite_cats.domain.repository.FavoriteCatsRepository
import dagger.Module
import dagger.Provides

@Module
class FavoriteCatsModule {

    @Provides
    fun provideFavoriteCatsRepository(db: FavoriteCatsDatabase): FavoriteCatsRepository {
        return FavoriteCatsRepositoryImpl(db)
    }
}
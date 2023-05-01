package com.example.cats_list.di

import com.example.cats_list.data.repository.CatsRepositoryImpl
import com.example.cats_list.domain.repository.CatsRepository
import com.example.storage.data.CatsApi
import dagger.Module
import dagger.Provides

@Module
class CatsListModule {

    @Provides
    fun provideCatsRepository(api: CatsApi): CatsRepository {
        return CatsRepositoryImpl(api)
    }
}
package com.example.favorite_cats.di

import com.example.database.di.DataModule
import com.example.favorite_cats.presentation.FavoriteCatsFragment
import com.example.favorite_cats.presentation.FavoriteCatsViewModelFactory
import dagger.Component

@Component(
    modules = [DataModule::class, FavoriteCatsModule::class],
    dependencies = [FavoriteCatsComponentDependencies::class]
)
interface FavoriteCatsComponent {

    fun injectFavoriteCatsFragment(favoriteCatsFragment: FavoriteCatsFragment)

    fun getViewModelFactory(): FavoriteCatsViewModelFactory
}

package com.example.favorite_cats.di

import com.example.favorite_cats.FavoriteScreens
import com.github.terrakok.cicerone.Router

interface FavoriteCatsComponentDependencies {

    fun getRouter(): Router

    fun getScreen(): FavoriteScreens
}
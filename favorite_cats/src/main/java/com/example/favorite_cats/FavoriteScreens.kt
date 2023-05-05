package com.example.favorite_cats

import com.github.terrakok.cicerone.androidx.FragmentScreen

interface FavoriteScreens {

    fun catDescriptionFragment(cat: FavoriteCat) : FragmentScreen
}
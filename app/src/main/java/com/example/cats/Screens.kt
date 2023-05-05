package com.example.cats

import com.example.cat_description.models.CatDescription
import com.example.cat_description.presentation.CatDescriptionFragment
import com.example.cats_list.Cat
import com.example.cats_list.presentation.CatsListFragment
import com.example.cats_list.presentation.Screens
import com.example.favorite_cats.FavoriteCat
import com.example.favorite_cats.FavoriteScreens
import com.example.favorite_cats.presentation.FavoriteCatsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen


class Screen : Screens, FavoriteScreens {
    override fun favoriteCatsFragment() = FragmentScreen {
        FavoriteCatsFragment.newInstance()
    }

    override fun catDescriptionFragment(cat: Cat) = FragmentScreen {
        CatDescriptionFragment.newInstance(mapCatToCatDescription(cat))
    }

    private fun mapCatToCatDescription(cat: Cat): CatDescription {
        return CatDescription(
            id = cat.id,
            url = cat.url
        )
    }

    fun catListFragment() = FragmentScreen {
        CatsListFragment.newInstance()
    }

    override fun catDescriptionFragment(cat: FavoriteCat) = FragmentScreen {
        CatDescriptionFragment.newInstance(mapFavoriteCatToCatDescription(cat))
    }
    private fun mapFavoriteCatToCatDescription(cat: FavoriteCat): CatDescription {
        return CatDescription(id = cat.id, url = cat.url)
    }
}


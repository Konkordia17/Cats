package com.example.cats

import com.example.cat_description.models.CatDescription
import com.example.cat_description.presentation.CatDescriptionFragment
import com.example.cats_list.Cat
import com.example.cats_list.presentation.CatsListFragment
import com.example.cats_list.presentation.Screens
import com.github.terrakok.cicerone.androidx.FragmentScreen


class Screen : Screens {
    fun catsFragment() = FragmentScreen {
        CatsListFragment.newInstance()
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
}


package com.example.cats_list.presentation

import com.example.cat_description.models.CatDescription
import com.example.cat_description.presentation.CatDescriptionFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun catDescriptionFragment(cat: CatDescription) = FragmentScreen {
        CatDescriptionFragment.newInstance(cat) }
}
package com.example.cats

import com.example.cats_list.presentation.CatsListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen


object Screens {

    fun catsFragment() = FragmentScreen {
        CatsListFragment.newInstance() }
}

package com.example.cats_list.presentation

import com.example.cats_list.Cat
import com.github.terrakok.cicerone.androidx.FragmentScreen


interface Screens {
    fun catDescriptionFragment(cat: Cat) : FragmentScreen

}
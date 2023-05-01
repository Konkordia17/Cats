package com.example.cats_list.di

import com.example.cats_list.presentation.CatsListFragment
import com.example.cats_list.presentation.ViewModelFactory

interface CatsListComponent {

    fun injectCatsListFragment(catsListFragment: CatsListFragment)

    fun getViewModelFactory(): ViewModelFactory

}
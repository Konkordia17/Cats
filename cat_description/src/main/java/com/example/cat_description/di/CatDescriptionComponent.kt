package com.example.cat_description.di

import com.example.cat_description.presentation.CatDescriptionFragment
import com.example.cat_description.presentation.CatDescriptionViewModelFactory
import dagger.Component

@Component(dependencies = [CatDescriptionComponentDependencies::class])
interface CatDescriptionComponent {

    fun injectCatDescriptionFragment(catDescription: CatDescriptionFragment)

    fun getViewModelFactory(): CatDescriptionViewModelFactory
}
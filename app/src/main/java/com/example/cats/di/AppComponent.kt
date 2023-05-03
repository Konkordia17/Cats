package com.example.cats.di

import com.example.cat_description.di.CatDescriptionComponentDependencies
import com.example.cats.MainActivity
import com.example.cats_list.di.CatsListComponentDependencies
import com.example.cats_list.presentation.Screens
import com.github.terrakok.cicerone.Router
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NavigationModule::class])
interface AppComponent: CatsListComponentDependencies, CatDescriptionComponentDependencies {
    override fun getRouter(): Router

    override fun getScreens(): Screens
    fun injectMainActivity(mainActivity: MainActivity)

}
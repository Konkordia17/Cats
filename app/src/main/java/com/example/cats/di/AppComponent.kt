package com.example.cats.di

import com.example.cats.MainActivity
import com.example.cats_list.di.CatsListComponentDependencies
import com.github.terrakok.cicerone.Router
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NavigationModule::class])
interface AppComponent: CatsListComponentDependencies {
    override fun getRouter(): Router
    fun injectMainActivity(mainActivity: MainActivity)

}
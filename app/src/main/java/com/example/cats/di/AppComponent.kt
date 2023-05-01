package com.example.cats.di

import com.example.cats.MainActivity
import com.example.cats_list.presentation.CatsListFragment
import com.example.cats_list.di.CatsListComponent
import com.example.cats_list.di.CatsListModule
import com.example.cats_list.presentation.ViewModelFactory
import com.example.storage.di.StorageModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NavigationModule::class, StorageModule::class, CatsListModule::class])
interface AppComponent : CatsListComponent {

    fun injectMainActivity(mainActivity: MainActivity)

    override fun injectCatsListFragment(catsListFragment: CatsListFragment)

    override fun getViewModelFactory(): ViewModelFactory

}
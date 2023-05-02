package com.example.cats_list.di

import com.example.cats_list.presentation.CatsListFragment
import com.example.cats_list.presentation.ViewModelFactory
import com.example.storage.di.StorageModule
import dagger.Component

@Component(
    modules = [StorageModule::class, CatsListModule::class],
    dependencies = [CatsListComponentDependencies::class]
)
interface CatsListComponent {

    fun injectCatsListFragment(catsListFragment: CatsListFragment)

    fun getViewModelFactory(): ViewModelFactory

}
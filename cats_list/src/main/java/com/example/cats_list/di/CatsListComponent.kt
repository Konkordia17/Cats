package com.example.cats_list.di

import com.example.cats_list.presentation.CatsListFragment
import com.example.cats_list.presentation.CatsListViewModelFactory
import com.example.database.di.DataModule
import com.example.storage.di.StorageModule
import dagger.Component

@Component(
    modules = [StorageModule::class, CatsListModule::class, DataModule::class],
    dependencies = [CatsListComponentDependencies::class]
)
interface CatsListComponent {

    fun injectCatsListFragment(catsListFragment: CatsListFragment)

    fun getViewModelFactory(): CatsListViewModelFactory

}
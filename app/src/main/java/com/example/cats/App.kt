package com.example.cats

import android.app.Application
import com.example.cat_description.di.CatDescriptionComponentDependencies
import com.example.cat_description.di.CatDescriptionComponentDependenciesProvider
import com.example.cats.di.AppComponent
import com.example.cats.di.DaggerAppComponent
import com.example.cats_list.di.CatsListComponentDependencies
import com.example.cats_list.di.CatsListComponentDependenciesProvider
import com.example.favorite_cats.di.FavoriteCatsComponentDependencies
import com.example.favorite_cats.di.FavoriteCatsComponentDependenciesProvider

class App : Application(), CatsListComponentDependenciesProvider,
    CatDescriptionComponentDependenciesProvider,
    FavoriteCatsComponentDependenciesProvider {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
    }

    override fun getCatsListComponentDependencies(): CatsListComponentDependencies {
        return appComponent
    }

    override fun getCatDescriptionComponentDependencies(): CatDescriptionComponentDependencies {
        return appComponent
    }

    override fun getFavoriteCatsComponentDependencies(): FavoriteCatsComponentDependencies {
        return appComponent
    }
}
package com.example.cats

import android.app.Application
import com.example.cats.di.AppComponent
import com.example.cats.di.DaggerAppComponent
import com.example.cats_list.di.CatsListComponentDependencies
import com.example.cats_list.di.CatsListComponentDependenciesProvider

class App: Application(), CatsListComponentDependenciesProvider {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()
    }

    override fun getCatsListComponentDependencies(): CatsListComponentDependencies {
       return appComponent
    }
}
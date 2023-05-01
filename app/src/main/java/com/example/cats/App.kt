package com.example.cats

import android.app.Application
import com.example.cats.di.AppComponent
import com.example.cats.di.DaggerAppComponent
import com.example.cats_list.di.CatsListComponent
import com.example.cats_list.di.CatsListComponentProvider

class App: Application(), CatsListComponentProvider {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()

    }

    override fun getCatsListComponent(): CatsListComponent {
        return appComponent
    }
}
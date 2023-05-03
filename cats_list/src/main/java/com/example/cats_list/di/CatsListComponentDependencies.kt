package com.example.cats_list.di

import com.example.cats_list.presentation.Screens
import com.github.terrakok.cicerone.Router

interface CatsListComponentDependencies {
    fun getRouter(): Router

    fun getScreens(): Screens
}
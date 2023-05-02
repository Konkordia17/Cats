package com.example.cats_list.di

import com.github.terrakok.cicerone.Router

interface CatsListComponentDependencies {
    fun getRouter(): Router
}
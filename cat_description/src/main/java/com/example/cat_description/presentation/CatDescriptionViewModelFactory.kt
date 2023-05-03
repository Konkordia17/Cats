package com.example.cat_description.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class CatDescriptionViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            CatDescriptionViewModel::class.java -> CatDescriptionViewModel()
            else -> throw IllegalArgumentException("Unknown ViewModel class")

        } as T
    }
}

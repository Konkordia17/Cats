package com.example.cats_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cats_list.domain.use_cases.GetCatsUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    val catsUseCase: GetCatsUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            CatsListViewModel::class.java -> CatsListViewModel(catsUseCase)
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
    }
}
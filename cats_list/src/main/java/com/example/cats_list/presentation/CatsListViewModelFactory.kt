package com.example.cats_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cats_list.domain.use_cases.DeleteCatFromDbUseCase
import com.example.cats_list.domain.use_cases.GetCatsFromDbUseCase
import com.example.cats_list.domain.use_cases.GetCatsUseCase
import com.example.cats_list.domain.use_cases.SetCatToFavoritesUseCase
import javax.inject.Inject

class CatsListViewModelFactory @Inject constructor(
    private val catsUseCase: GetCatsUseCase,
    private val setCatToFavoritesUseCase: SetCatToFavoritesUseCase,
    private val getCatsFromDbUseCase: GetCatsFromDbUseCase,
    private val deleteCatFromDbUseCase: DeleteCatFromDbUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            CatsListViewModel::class.java -> CatsListViewModel(
                catsUseCase,
                setCatToFavoritesUseCase,
                getCatsFromDbUseCase,
                deleteCatFromDbUseCase
            )

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
    }
}
package com.example.favorite_cats.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favorite_cats.domain.use_cases.DeleteAllFavoriteCatsUseCase
import com.example.favorite_cats.domain.use_cases.DeleteFavoriteCatFromDbUseCase
import com.example.favorite_cats.domain.use_cases.GetFavoriteCatsUseCase
import javax.inject.Inject

class FavoriteCatsViewModelFactory @Inject constructor(
    private val getFavoriteCatsUseCase: GetFavoriteCatsUseCase,
    private val deleteFavoriteCatFromDbUseCase: DeleteFavoriteCatFromDbUseCase,
    private val deleteAllFavoriteCatsUseCase: DeleteAllFavoriteCatsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            FavoriteCatsViewModel::class.java -> FavoriteCatsViewModel(
                getFavoriteCatsUseCase,
                deleteFavoriteCatFromDbUseCase,
                deleteAllFavoriteCatsUseCase
            )

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
                as T
    }
}
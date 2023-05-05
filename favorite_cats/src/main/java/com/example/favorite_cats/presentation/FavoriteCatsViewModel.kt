package com.example.favorite_cats.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.favorite_cats.FavoriteCat
import com.example.favorite_cats.domain.use_cases.DeleteAllFavoriteCatsUseCase
import com.example.favorite_cats.domain.use_cases.DeleteFavoriteCatFromDbUseCase
import com.example.favorite_cats.domain.use_cases.GetFavoriteCatsUseCase
import com.example.ui.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FavoriteCatsViewModel(
    private val getFavoriteCatsUseCase: GetFavoriteCatsUseCase,
    private val deleteFavoriteCatFromDbUseCase: DeleteFavoriteCatFromDbUseCase,
    private val deleteAllFavoriteCatsUseCase: DeleteAllFavoriteCatsUseCase
) :
    ViewModel() {

    private val _favoriteCatsList = MutableLiveData<List<FavoriteCat>>()
    val favoriteCatsList: LiveData<List<FavoriteCat>> = _favoriteCatsList

    private val isCatDeleted: SingleLiveEvent<Boolean> = SingleLiveEvent()
    fun getUpdateFavoritesAction(): SingleLiveEvent<Boolean> {
        return isCatDeleted
    }

    private fun onUpdatedFavorites(isCatAdded: Boolean?) {
        isCatDeleted.postValue(isCatAdded)
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
        onUpdatedFavorites(false)
    }
    private val scope = CoroutineScope(Dispatchers.IO)

    fun getFavoriteCats() {
        scope.launch(handler) {
            _favoriteCatsList.postValue(getFavoriteCatsUseCase.getFavoriteCats())
        }
    }

    fun deleteFavoriteCat(cat: FavoriteCat) {
        scope.launch(handler) {
            deleteFavoriteCatFromDbUseCase.deleteCatFromDb(cat)
            onUpdatedFavorites(true)
            getFavoriteCats()
        }
    }

    fun deleteAllCatsFromFavorites() {
        scope.launch(handler) {
            deleteAllFavoriteCatsUseCase.deleteAllFavoriteCats()
            getFavoriteCats()
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
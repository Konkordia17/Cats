package com.example.cats_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cats_list.Cat
import com.example.cats_list.domain.use_cases.DeleteCatFromDbUseCase
import com.example.cats_list.domain.use_cases.GetCatsFromDbUseCase
import com.example.cats_list.domain.use_cases.GetCatsUseCase
import com.example.cats_list.domain.use_cases.SetCatToFavoritesUseCase
import com.example.cats_list.presentation.CatsListFragment.Companion.CATS_LIMIT
import com.example.ui.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CatsListViewModel(
    private val catsUseCase: GetCatsUseCase,
    private val setCatToFavoritesUseCase: SetCatToFavoritesUseCase,
    private val getCatsFromDbUseCase: GetCatsFromDbUseCase,
    private val deleteCatFromDbUseCase: DeleteCatFromDbUseCase
) : ViewModel() {

    private val _catsList = MutableLiveData<List<Cat>>()
    val catsList: LiveData<List<Cat>> = _catsList

    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    private val scope = CoroutineScope(Dispatchers.IO)

    private var currentCatsList: List<Cat> = emptyList()

    private val isCatAddedToFavorites: SingleLiveEvent<Boolean> = SingleLiveEvent()
    fun getUpdateFavoritesAction(): SingleLiveEvent<Boolean> {
        return isCatAddedToFavorites
    }

    private fun onUpdatedFavorites(isCatAdded: Boolean?) {
        isCatAddedToFavorites.postValue(isCatAdded)
    }

    fun getCats(limit: Int) {
        scope.launch(handler) {
            val cats = catsUseCase.getCats(limit)
            val favoriteCats = getCatsFromDbUseCase.getFavoriteCats()
            currentCatsList = currentCatsList + cats
            _catsList.postValue(changeCatsDataIfInFavorites(currentCatsList, favoriteCats))
        }
    }

    private fun changeCatsDataIfInFavorites(cats: List<Cat>, favoriteCats: List<Cat>): List<Cat> {
        return cats.map { cat ->
            if (favoriteCats.contains(cat)) {
                cat.copy(isFavorite = true)
            } else {
                cat
            }
        }
    }

    fun setOrDeleteCatFromFavorites(favoriteCat: Cat) {
        if (!favoriteCat.isFavorite) {
            setCatToFavorites(favoriteCat)
        } else {
            deleteCatFromFavorites(favoriteCat)
        }
    }

    private fun setCatToFavorites(favoriteCat: Cat) {
        scope.launch(handler) {
            setCatToFavoritesUseCase.setCatToFavorites(favoriteCat)
            onUpdatedFavorites(true)
            changeCatFavoriteState(favoriteCat = favoriteCat, isFavorite = true)
        }
    }

    private fun deleteCatFromFavorites(cat: Cat) {
        scope.launch(handler) {
            deleteCatFromDbUseCase.deleteCatFromFavorites(cat)
            onUpdatedFavorites(false)
            changeCatFavoriteState(favoriteCat = cat, isFavorite = false)
        }
    }

    private fun changeCatFavoriteState(favoriteCat: Cat, isFavorite: Boolean) {
        _catsList.postValue(_catsList.value?.map { cat ->
            if (cat == favoriteCat) {
                favoriteCat.copy(isFavorite = isFavorite)
            } else {
                cat
            }
        })
    }

    fun onPositionChanged(position: Int) {
        if (position >= currentCatsList.size - 10) {
            getCats(CATS_LIMIT)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
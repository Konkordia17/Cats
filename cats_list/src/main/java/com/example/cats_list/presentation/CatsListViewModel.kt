package com.example.cats_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cats_list.Cat
import com.example.cats_list.domain.use_cases.GetCatsUseCase
import com.example.cats_list.domain.use_cases.SetCatToFavoritesUseCase
import com.example.cats_list.presentation.CatsListFragment.Companion.CATS_LIMIT
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsListViewModel(
    private val catsUseCase: GetCatsUseCase,
    private val setCatToFavoritesUseCase: SetCatToFavoritesUseCase
) : ViewModel() {

    private val _catsList = MutableLiveData<List<Cat>>()
    val catsList: LiveData<List<Cat>> = _catsList
    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    private val scope = CoroutineScope(Dispatchers.IO)

    private var currentCatsList: List<Cat> = emptyList()

    fun getCats(limit: Int) {
        scope.launch(handler) {
            val cats = catsUseCase.getCats(limit)
            currentCatsList = currentCatsList + cats
            _catsList.postValue(currentCatsList)
        }
    }

    fun setCatToFavorites(cat: Cat, onCatAdded: () -> Unit) {
        scope.launch(handler) {
            setCatToFavoritesUseCase.setCatToFavorites(cat)
            withContext(Dispatchers.Main) {
                onCatAdded.invoke()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

    fun onPositionChanged(position: Int) {
        if (position >= currentCatsList.size - 1) {
            getCats(CATS_LIMIT)
        }
    }
}
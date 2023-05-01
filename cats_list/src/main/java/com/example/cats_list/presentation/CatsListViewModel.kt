package com.example.cats_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cats_list.Cat
import com.example.cats_list.domain.use_cases.GetCatsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CatsListViewModel(private val catsUseCase: GetCatsUseCase) : ViewModel() {

    private val _catsList = MutableLiveData<List<Cat>>()
    val catsList: LiveData<List<Cat>> = _catsList
    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    fun getCats(page: Int) {
        scope.launch(handler) {
            _catsList.postValue(catsUseCase.getCats(page))
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
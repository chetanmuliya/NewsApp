package com.example.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.helper.LatestNewsState
import com.example.newsapp.domain.usecases.ObserveLatestNewsCacheUseCases
import com.example.newsapp.domain.usecases.GetLatestNewsUseCases
import com.example.newsapp.domain.usecases.GetNewsCacheUseCases
import com.example.newsapp.domain.usecases.GetSearchNewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLatestNewsUseCases: GetLatestNewsUseCases,
    private val getNewsCacheUseCases: GetNewsCacheUseCases,
    private val observeLatestNewsCacheUseCases: ObserveLatestNewsCacheUseCases,
    private val searchNewsUseCases: GetSearchNewsUseCases,
): ViewModel() {

    private val _uiState = MutableStateFlow<LatestNewsState>(LatestNewsState.Loading)
    val uiState = _uiState.asStateFlow()

    val newsCache = observeLatestNewsCacheUseCases()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LatestNewsState.Loading
        )

    private val _searchState = MutableStateFlow<LatestNewsState>(LatestNewsState.Loading)
    val searchState = _searchState.asStateFlow()

    init {
        //without room direct api call
        //getLatestNews()
        getLatestNewsCache()
    }

    private fun getLatestNewsCache() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getNewsCacheUseCases()
            }
        }
    }

    fun getLatestNews(){
        viewModelScope.launch {
            _uiState.value = LatestNewsState.Loading
            try {
                val result = getLatestNewsUseCases().results
                _uiState.value = LatestNewsState.Success(result)
            }catch (e: CancellationException){
                throw e
            }catch (e: Exception){
                _uiState.value = LatestNewsState.Error(e.message?:"Something Went Wrong!!")
            }
        }
    }

    fun searchNews(q: String) {

        if (q.isNotBlank()) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    delay(500)
                    val state = searchNewsUseCases(q)
                    _searchState.value = state
                }
            }
        }

    }
}
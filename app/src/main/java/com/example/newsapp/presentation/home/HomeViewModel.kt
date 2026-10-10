package com.example.newsapp.presentation.home

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.helper.LatestNewsState
import com.example.newsapp.domain.usecases.GetLatestNewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLatestNewsUseCases: GetLatestNewsUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow<LatestNewsState>(LatestNewsState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getLatestNews()
    }

    fun getLatestNews(){
        viewModelScope.launch {
            val result = getLatestNewsUseCases()
            _uiState.value = LatestNewsState.Success(result)
        }
    }
}
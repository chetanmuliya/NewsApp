package com.example.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.helper.LatestNewsState
import com.example.newsapp.domain.usecases.ObserveLatestNewsCacheUseCases
import com.example.newsapp.domain.usecases.GetLatestNewsUseCases
import com.example.newsapp.domain.usecases.GetNewsByCategoryUseCases
import com.example.newsapp.domain.usecases.GetNewsCacheUseCases
import com.example.newsapp.domain.usecases.GetSearchNewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    private val getNewsByCategoryUseCases: GetNewsByCategoryUseCases,
): ViewModel() {

    private val _uiState = MutableStateFlow<LatestNewsState>(LatestNewsState.Loading)
    val uiState = _uiState.asStateFlow()

    val newsCache = observeLatestNewsCacheUseCases()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LatestNewsState.Loading
        )

    private val _searchState =
        MutableStateFlow<LatestNewsState>(LatestNewsState.Success(emptyList()))
    val searchState = _searchState.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    val getTabList = listOf("top","sports","health","food")
    private val _categoryState =
        MutableStateFlow<LatestNewsState>(LatestNewsState.Success(emptyList()))
    val categoryState = _categoryState.asStateFlow()

    init {
        //without room direct api call
        //getLatestNews()
        getLatestNewsCache()

        searchQuery
            .debounce(500)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .flatMapLatest { query ->
                flow {
                    emit(searchNewsUseCases(query))
                }.catch { e ->
                    if (e is CancellationException) throw e

                    emit(
                        LatestNewsState.Error(
                            e.message ?: "search failed"
                        )
                    )
                }
            }
            .flowOn(Dispatchers.IO)
            .onEach { state ->
                _searchState.value = state
            }.launchIn(viewModelScope)

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

        if (q.isBlank()) return

        searchQuery.value = q

    }

    fun onCategoryClicked(category: String) {

        viewModelScope.launch {
            val categoryNews = getNewsByCategoryUseCases(category)
            _categoryState.value = categoryNews

        }

    }
}
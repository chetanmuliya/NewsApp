package com.example.newsapp.domain.helper

import com.example.newsapp.domain.model.LatestNewsResponse

sealed interface LatestNewsState {

    object Loading: LatestNewsState
    class  Success(val data: LatestNewsResponse): LatestNewsState
    class  Error(val error : String): LatestNewsState

}
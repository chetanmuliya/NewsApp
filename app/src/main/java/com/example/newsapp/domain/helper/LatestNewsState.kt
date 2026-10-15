package com.example.newsapp.domain.helper

import com.example.newsapp.domain.model.Result

sealed interface LatestNewsState {

    object Loading: LatestNewsState
    class  Success(val data: List<Result>): LatestNewsState
    class  Error(val error : String): LatestNewsState

}
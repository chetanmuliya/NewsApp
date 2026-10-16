package com.example.newsapp.domain.repository

import com.example.newsapp.domain.helper.LatestNewsState
import com.example.newsapp.domain.model.LatestNewsResponse
import com.example.newsapp.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface LatestNewsRepository {

    //without dao direct api call
    suspend fun getLatestNews(): LatestNewsResponse

    suspend fun refreshLatestNews()

    fun getLatestNewsCache(): Flow<LatestNewsState>

    suspend fun searchNews(query: String) : LatestNewsState
}
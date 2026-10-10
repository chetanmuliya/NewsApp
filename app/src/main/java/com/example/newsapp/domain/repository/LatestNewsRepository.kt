package com.example.newsapp.domain.repository

import com.example.newsapp.domain.model.LatestNewsResponse

interface LatestNewsRepository {
    suspend fun getLatestNews(): LatestNewsResponse
}
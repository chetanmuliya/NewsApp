package com.example.newsapp.data.repository

import com.example.newsapp.data.mapper.toDomain
import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.domain.model.LatestNewsResponse
import com.example.newsapp.domain.repository.LatestNewsRepository
import javax.inject.Inject

class LatestNewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
): LatestNewsRepository {

    override suspend fun getLatestNews(): LatestNewsResponse {
        return api.getLatestNews().toDomain()
    }

}
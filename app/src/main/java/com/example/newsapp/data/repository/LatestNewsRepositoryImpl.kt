package com.example.newsapp.data.repository

import android.util.Log
import com.example.newsapp.data.local.dao.ArticlesDao
import com.example.newsapp.data.mapper.toDomain
import com.example.newsapp.data.mapper.toEntity
import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.domain.helper.LatestNewsState
import com.example.newsapp.domain.model.LatestNewsResponse
import com.example.newsapp.domain.repository.LatestNewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LatestNewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val dao: ArticlesDao
): LatestNewsRepository {

    //without dao direct api call
    override suspend fun getLatestNews(): LatestNewsResponse {
        return api.getLatestNews().toDomain()
    }

    override suspend fun refreshLatestNews() {
        val articles = api.getLatestNews()
        dao.replaceArticles(articles.results.map { it.toEntity() })
    }

    override fun getLatestNewsCache(): Flow<LatestNewsState> {
        return dao.getAllArticles().map { entities ->
            LatestNewsState.Success(
                entities.map { it.toDomain() }
            )
        }

    }

    override suspend fun searchNews(query: String): LatestNewsState {
        return api.searchNews(query).results.let {  response ->
            LatestNewsState.Success(
                response.map { it.toDomain() }
            )
        }
    }

    override suspend fun getByNewsCategory(category: String): LatestNewsState {
        return api.newsByCategory(category).results.let { response ->
            LatestNewsState.Success(
                response.map { it.toDomain() }
            )
        }
    }

}
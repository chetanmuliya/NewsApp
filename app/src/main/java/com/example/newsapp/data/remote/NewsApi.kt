package com.example.newsapp.data.remote

import com.example.newsapp.data.dto.LatestNewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("latest")
    suspend fun getLatestNews(): LatestNewsResponseDto

    @GET("latest")
    suspend fun searchNews(
        @Query("q") query : String
    ):LatestNewsResponseDto

    @GET("latest")
    suspend fun newsByCategory(
        @Query("category") category : String
    ):LatestNewsResponseDto

}
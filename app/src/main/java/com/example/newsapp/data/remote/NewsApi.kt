package com.example.newsapp.data.remote

import com.example.newsapp.data.dto.LatestNewsResponseDto
import retrofit2.http.GET

interface NewsApi {

    @GET("latest")
    suspend fun getLatestNews(): LatestNewsResponseDto

}
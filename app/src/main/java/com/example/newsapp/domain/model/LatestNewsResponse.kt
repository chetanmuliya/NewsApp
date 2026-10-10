package com.example.newsapp.domain.model

data class LatestNewsResponse(
    val nextPage: String,
    val results: List<Result>,
    val status: String,
    val totalResults: Int
)
package com.example.newsapp.data.dto

data class LatestNewsResponseDto(
    val nextPage: String,
    val results: List<ResultDto>,
    val status: String,
    val totalResults: Int
)
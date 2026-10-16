package com.example.newsapp.domain.usecases

import com.example.newsapp.domain.helper.LatestNewsState
import com.example.newsapp.domain.repository.LatestNewsRepository
import javax.inject.Inject

class GetSearchNewsUseCases @Inject constructor(
    private val latestNewsRepository: LatestNewsRepository
) {
    suspend operator fun invoke(query: String): LatestNewsState =
        latestNewsRepository.searchNews(query = query)
}
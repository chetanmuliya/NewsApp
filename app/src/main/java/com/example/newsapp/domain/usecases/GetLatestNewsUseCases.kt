package com.example.newsapp.domain.usecases

import com.example.newsapp.domain.repository.LatestNewsRepository
import javax.inject.Inject

class GetLatestNewsUseCases @Inject constructor(
    private val latestNewsRepository: LatestNewsRepository
) {
    suspend operator fun invoke() = latestNewsRepository.getLatestNews()
}
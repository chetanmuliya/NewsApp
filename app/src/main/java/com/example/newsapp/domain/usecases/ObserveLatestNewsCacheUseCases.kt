package com.example.newsapp.domain.usecases

import com.example.newsapp.domain.repository.LatestNewsRepository
import javax.inject.Inject

class ObserveLatestNewsCacheUseCases @Inject constructor(
    private val latestNewsRepository: LatestNewsRepository
) {
    operator fun invoke() = latestNewsRepository.getLatestNewsCache()
}
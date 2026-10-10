package com.example.newsapp.di

import com.example.newsapp.data.repository.LatestNewsRepositoryImpl
import com.example.newsapp.domain.repository.LatestNewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindLatestNewsRepository(latestNewsRepository: LatestNewsRepositoryImpl): LatestNewsRepository
}
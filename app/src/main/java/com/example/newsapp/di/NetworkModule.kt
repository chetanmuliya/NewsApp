package com.example.newsapp.di

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.remote.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            addInterceptor{ chain ->
                val newUrl = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("apikey", BuildConfig.API_KEY)
                    .build()

                val request = chain.request().newBuilder().url(newUrl).build()

                chain.proceed(request)
            }
        }.build()

    @Singleton @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton @Provides
    fun provideApi(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)

}
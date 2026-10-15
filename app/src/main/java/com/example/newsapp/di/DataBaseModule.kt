package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.data.local.ArticlesDatabase
import com.example.newsapp.data.local.dao.ArticlesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides @Singleton
    fun provideAppDataBase(
        @ApplicationContext context: Context
    ): ArticlesDatabase =
        Room.databaseBuilder(
            context,
            ArticlesDatabase::class.java,
            "article_database"
        ).build()

    @Provides
    fun userDao(
        articlesDao: ArticlesDatabase
    ): ArticlesDao {
        return articlesDao.articleDao()
    }

}
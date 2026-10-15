package com.example.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.local.entity.ResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles : List<ResultEntity>)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<ResultEntity>>

    @Query("SELECT * FROM articles WHERE article_id==:id")
    suspend fun getArticlesById(id:String): ResultEntity

    @Query("DELETE FROM articles WHERE article_id==:id")
    fun deleteArticleById(id: String)

}
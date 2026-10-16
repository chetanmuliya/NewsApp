package com.example.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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
    suspend fun deleteArticleById(id: String)

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticle()

    @Transaction
    suspend fun replaceArticles(articles: List<ResultEntity>){
        deleteAllArticle()
        insertArticles(articles = articles)
    }

}
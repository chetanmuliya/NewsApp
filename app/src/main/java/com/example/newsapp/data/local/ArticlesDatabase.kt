package com.example.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.local.dao.ArticlesDao
import com.example.newsapp.data.local.entity.ResultEntity

@Database(
    entities = [ResultEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ArticlesDatabase : RoomDatabase(){
    abstract fun articleDao():ArticlesDao
}
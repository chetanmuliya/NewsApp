package com.example.newsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "Articles")
data class ResultEntity(
    val ai_org: String ?= null,
    val ai_region: String?= null,
    val ai_summary: String?= null,
    val ai_tagggg: String?= null,
    @PrimaryKey
    val article_id: String,
    val category: List<String>?=null,
    val content: String ?= null,
    val country: List<String>,
    val creator: List<String>?=null,
    val datatype: String?= null,
    val description: String?= null,
    val duplicate: Boolean?= null,
    val fetched_at: String?= null,
    val image_url: String?= null,
    val keywords: List<String>?=null,
    val language: String?= null,
    val link: String?= null,
    val pubDate: String?= null,
    val pubDateTZ: String?= null,
    val sentiment: String?= null,
    val source_icon: String?= null,
    val source_id: String?= null,
    val source_name: String?= null,
    val source_priority: Int?= null,
    val source_url: String?= null,
    val title: String?= null,
    val video_url: String?= null
)
package com.example.newsapp.data.mapper

import com.example.newsapp.data.dto.LatestNewsResponseDto
import com.example.newsapp.data.dto.ResultDto
import com.example.newsapp.domain.model.LatestNewsResponse
import com.example.newsapp.domain.model.Result
import kotlin.String

fun LatestNewsResponseDto.toDomain() = LatestNewsResponse(
    nextPage = nextPage,
    results = results.map { it.toDomain() },
    status = status,
    totalResults = totalResults
)

fun ResultDto.toDomain() = Result(
    ai_org = ai_org,
    ai_region = ai_region,
    ai_summary = ai_summary,
    ai_tag = ai_tagggg,
    article_id = article_id,
    category = category,
    content = content,
    country = country,
    creator = creator,
    datatype = datatype,
    description = description,
    duplicate = duplicate,
    fetched_at = fetched_at,
    image_url = image_url,
    keywords = keywords,
    language = language,
    link = link,
    pubDate = pubDate,
    pubDateTZ = pubDateTZ,
    sentiment = sentiment,
    sentiment_stats = sentiment_statsssss,//ssss
    source_icon = source_icon,
    source_id = source_id,
    source_name = source_name,
    source_priority = source_priority,
    source_url = source_url,
    title = title,
    video_url = video_url
)
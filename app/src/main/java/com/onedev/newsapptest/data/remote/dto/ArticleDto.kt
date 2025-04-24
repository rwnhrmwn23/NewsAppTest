package com.onedev.newsapptest.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ArticleDto(
    val id: Int,
    val title: String,
    val summary: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("published_at")
    val publishedAt: String
)

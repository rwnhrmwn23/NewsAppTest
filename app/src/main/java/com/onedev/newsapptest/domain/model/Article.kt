package com.onedev.newsapptest.domain.model

data class Article(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val publishedAt: String
)

package com.onedev.newsapptest.data.remote.response

import com.onedev.newsapptest.data.remote.dto.ArticleDto

data class ArticleResponse(
    val results: List<ArticleDto>
)
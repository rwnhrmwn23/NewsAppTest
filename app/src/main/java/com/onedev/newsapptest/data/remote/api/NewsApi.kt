package com.onedev.newsapptest.data.remote.api

import com.onedev.newsapptest.data.remote.dto.ArticleDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("articles")
    suspend fun getArticles(
        @Query("limit") limit: Int = 10,
        @Query("search") search: String = ""
    ): ArticleResponse
}

data class ArticleResponse(
    val results: List<ArticleDto>
)
package com.onedev.newsapptest.data.remote.api

import com.onedev.newsapptest.data.remote.response.ArticleResponse
import com.onedev.newsapptest.data.remote.response.BlogResponse
import com.onedev.newsapptest.data.remote.response.NewsSiteResponse
import com.onedev.newsapptest.data.remote.response.ReportsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("articles")
    suspend fun getArticles(
        @Query("limit") limit: Int = 10,
        @Query("search") search: String? = "",
        @Query("news_site") site: String? = ""
    ): ArticleResponse

    @GET("blogs")
    suspend fun getBlogs(
        @Query("limit") limit: Int = 10,
        @Query("search") search: String? = "",
        @Query("news_site") site: String? = ""
    ): BlogResponse

    @GET("reports")
    suspend fun getReports(
        @Query("limit") limit: Int = 10,
        @Query("search") search: String? = "",
        @Query("news_site") site: String? = ""
    ): ReportsResponse

    @GET("info/")
    suspend fun getNewsInfo(): NewsSiteResponse

}
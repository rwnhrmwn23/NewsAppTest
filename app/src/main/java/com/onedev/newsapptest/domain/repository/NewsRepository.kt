package com.onedev.newsapptest.domain.repository

import com.onedev.newsapptest.domain.model.News

interface NewsRepository {
    suspend fun getArticles(search: String? = null): List<News>
    suspend fun getBlogs(search: String? = null): List<News>
    suspend fun getReport(search: String? = null): List<News>
}
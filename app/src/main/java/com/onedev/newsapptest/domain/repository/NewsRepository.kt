package com.onedev.newsapptest.domain.repository

import com.onedev.newsapptest.domain.model.News
import com.onedev.newsapptest.domain.model.NewsSite

interface NewsRepository {
    suspend fun getArticles(search: String? = null, newsSite: String? = null): List<News>
    suspend fun getBlogs(search: String? = null, newsSite: String? = null): List<News>
    suspend fun getReport(search: String? = null, newsSite: String? = null): List<News>
    suspend fun getNewsSite(): NewsSite
}
package com.onedev.newsapptest.domain.repository

import com.onedev.newsapptest.domain.model.Article

interface ArticleRepository {
    suspend fun getArticles(): List<Article>
}
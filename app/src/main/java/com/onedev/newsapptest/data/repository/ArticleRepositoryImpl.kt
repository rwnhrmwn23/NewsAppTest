package com.onedev.newsapptest.data.repository

import com.onedev.newsapptest.data.remote.api.NewsApi
import com.onedev.newsapptest.domain.model.Article
import com.onedev.newsapptest.domain.repository.ArticleRepository

class ArticleRepositoryImpl(
    private val api: NewsApi
) : ArticleRepository {
    override suspend fun getArticles(): List<Article> {
        return api.getArticles().results.map {
            Article(
                id = it.id,
                title = it.title,
                imageUrl = it.image_url,
                publishedAt = it.published_at
            )
        }
    }
}

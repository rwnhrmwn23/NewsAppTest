package com.onedev.newsapptest.data.repository

import com.onedev.newsapptest.data.remote.api.NewsApi
import com.onedev.newsapptest.domain.model.Article
import com.onedev.newsapptest.domain.repository.ArticleRepository

class ArticleRepositoryImpl(
    private val api: NewsApi
) : ArticleRepository {
    override suspend fun getArticles(search: String?): List<Article> {
        return api.getArticles(search = search).results.map {
            Article(
                id = it.id,
                title = it.title,
                imageUrl = it.imageUrl,
                summary = it.summary,
                publishedAt = it.publishedAt,
                launches = it.launches.map { l -> Article.Launch(l.launchId, l.provider) },
                events = it.events.map { e -> Article.Event(e.eventId, e.provider) }
            )
        }
    }
}

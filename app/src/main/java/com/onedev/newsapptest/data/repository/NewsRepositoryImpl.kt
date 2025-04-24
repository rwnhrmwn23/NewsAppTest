package com.onedev.newsapptest.data.repository

import com.onedev.newsapptest.data.remote.api.NewsApi
import com.onedev.newsapptest.domain.model.News
import com.onedev.newsapptest.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {
    override suspend fun getArticles(search: String?): List<News> {
        return api.getArticles(search = search).results.map {
            News(
                id = it.id,
                title = it.title,
                imageUrl = it.imageUrl,
                summary = it.summary,
                publishedAt = it.publishedAt,
                launches = it.launches.map { l -> News.Launch(l.launchId, l.provider) },
                events = it.events.map { e -> News.Event(e.eventId, e.provider) }
            )
        }
    }

    override suspend fun getBlogs(search: String?): List<News> {
        return api.getBlogs(search = search).results.map {
            News(
                id = it.id,
                title = it.title,
                imageUrl = it.imageUrl,
                summary = it.summary,
                publishedAt = it.publishedAt,
                launches = it.launches.map { l -> News.Launch(l.launchId, l.provider) },
                events = it.events.map { e -> News.Event(e.eventId, e.provider) }
            )
        }
    }
}

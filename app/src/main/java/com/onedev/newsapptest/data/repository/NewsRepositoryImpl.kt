package com.onedev.newsapptest.data.repository

import com.onedev.newsapptest.data.remote.api.NewsApi
import com.onedev.newsapptest.domain.model.News
import com.onedev.newsapptest.domain.model.NewsSite
import com.onedev.newsapptest.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {
    override suspend fun getArticles(search: String?, newsSite: String?): List<News> {
        return api.getArticles(search = search, site = newsSite).results.map {
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

    override suspend fun getBlogs(search: String?, newsSite: String?): List<News> {
        return api.getBlogs(search = search, site = newsSite).results.map {
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

    override suspend fun getReport(search: String?, newsSite: String?): List<News> {
        return api.getReports(search = search, site = newsSite).results.map {
            News(
                id = it.id,
                title = it.title,
                imageUrl = it.imageUrl,
                summary = it.summary,
                publishedAt = it.publishedAt
            )
        }
    }

    override suspend fun getNewsSite(): NewsSite {
        val dtp = api.getNewsInfo()
        return NewsSite(
            version = dtp.version,
            newsSites = dtp.newsSites,
        )
    }
}

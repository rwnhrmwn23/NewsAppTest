package com.onedev.newsapptest.data.repository

import com.onedev.newsapptest.data.remote.api.NewsApi
import com.onedev.newsapptest.domain.model.News
import com.onedev.newsapptest.domain.model.NewsSite
import com.onedev.newsapptest.domain.repository.NewsRepository
import com.onedev.newsapptest.utils.safeApiFlow

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {

    override fun getArticles(search: String?, newsSite: String?) = safeApiFlow {
        api.getArticles(search = search, site = newsSite).results.map {
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


    override fun getBlogs(search: String?, newsSite: String?) = safeApiFlow {
        api.getBlogs(search = search, site = newsSite).results.map {
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

    override fun getReport(search: String?, newsSite: String?) = safeApiFlow {
        api.getReports(search = search, site = newsSite).results.map {
            News(
                id = it.id,
                title = it.title,
                imageUrl = it.imageUrl,
                summary = it.summary,
                publishedAt = it.publishedAt,
                launches = emptyList(),
                events = emptyList()
            )
        }
    }

    override fun getNewsSite() = safeApiFlow {
        val dto = api.getNewsInfo()
        NewsSite(
            version = dto.version,
            newsSites = dto.newsSites
        )
    }
}


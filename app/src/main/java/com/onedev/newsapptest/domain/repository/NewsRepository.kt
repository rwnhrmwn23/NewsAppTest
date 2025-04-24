package com.onedev.newsapptest.domain.repository

import com.onedev.newsapptest.domain.model.News
import com.onedev.newsapptest.domain.model.NewsSite
import com.onedev.newsapptest.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getArticles(search: String?, newsSite: String?): Flow<Resource<List<News>>>
    fun getBlogs(search: String?, newsSite: String?): Flow<Resource<List<News>>>
    fun getReport(search: String?, newsSite: String?): Flow<Resource<List<News>>>
    fun getNewsSite(): Flow<Resource<NewsSite>>
}

package com.onedev.newsapptest.domain.usecase

import com.onedev.newsapptest.domain.model.News
import com.onedev.newsapptest.domain.repository.NewsRepository

class GetReportUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(search: String? = null, newsSite: String? = null):
            List<News> = repository.getReport(search = search, newsSite = newsSite)
}
package com.onedev.newsapptest.domain.usecase

import com.onedev.newsapptest.domain.repository.NewsRepository

class GetReportUseCase(private val repository: NewsRepository) {
    operator fun invoke(search: String? = null, newsSite: String? = null) =
        repository.getReport(search = search, newsSite = newsSite)
}
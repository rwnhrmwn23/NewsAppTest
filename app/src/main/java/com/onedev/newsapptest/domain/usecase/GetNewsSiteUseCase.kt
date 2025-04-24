package com.onedev.newsapptest.domain.usecase

import com.onedev.newsapptest.domain.repository.NewsRepository

class GetNewsSiteUseCase(private val repository: NewsRepository) {
    operator fun invoke() = repository.getNewsSite()
}
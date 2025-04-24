package com.onedev.newsapptest.domain.usecase

import com.onedev.newsapptest.domain.repository.NewsRepository

class GetNewsSiteUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(): List<String> {
        return repository.getNewsSite().newsSites
    }
}
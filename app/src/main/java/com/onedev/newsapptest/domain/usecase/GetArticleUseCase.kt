package com.onedev.newsapptest.domain.usecase

import com.onedev.newsapptest.domain.repository.NewsRepository

class GetArticleUseCase(private val repository: NewsRepository) {
    operator fun invoke(search: String? = null, newsSite: String? = null) =
        repository.getArticles(search, newsSite)
}
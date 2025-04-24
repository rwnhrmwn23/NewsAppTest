package com.onedev.newsapptest.domain.usecase

import com.onedev.newsapptest.domain.model.Article
import com.onedev.newsapptest.domain.repository.ArticleRepository

class GetArticleUseCase(private val repository: ArticleRepository) {
    suspend operator fun invoke(): List<Article> = repository.getArticles()
}
package com.onedev.newsapptest.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.onedev.newsapptest.domain.model.News
import com.onedev.newsapptest.domain.usecase.GetArticleUseCase
import com.onedev.newsapptest.domain.usecase.GetBlogUseCase
import com.onedev.newsapptest.domain.usecase.GetReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticleUseCase,
    private val getBlogUseCase: GetBlogUseCase,
    private val getReportUseCase: GetReportUseCase,
) : ViewModel() {

    var news by mutableStateOf<List<News>>(emptyList())
        private set

    var isLoadingArticle by mutableStateOf(true)
        private set

    var blogs by mutableStateOf<List<News>>(emptyList())
        private set

    var isLoadingBlog by mutableStateOf(true)
        private set

    var reports by mutableStateOf<List<News>>(emptyList())
        private set

    var isLoadingReport by mutableStateOf(true)
        private set

    init {
        loadArticles()
        loadBlogs()
        loadReports()
    }

    private fun loadArticles(search: String? = null) {
        viewModelScope.launch {
            try {
                isLoadingArticle = true
                news = getArticlesUseCase(search = search)
            } finally {
                isLoadingArticle = false
            }
        }
    }

    private fun loadBlogs(search: String? = null) {
        viewModelScope.launch {
            try {
                isLoadingBlog = true
                blogs = getBlogUseCase(search = search)
            } finally {
                isLoadingBlog = false
            }
        }
    }

    private fun loadReports(search: String? = null) {
        viewModelScope.launch {
            try {
                isLoadingReport = true
                reports = getReportUseCase(search = search)
            } finally {
                isLoadingReport = false
            }
        }
    }
}

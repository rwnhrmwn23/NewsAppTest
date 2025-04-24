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
import com.onedev.newsapptest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticleUseCase,
    private val getBlogUseCase: GetBlogUseCase,
    private val getReportUseCase: GetReportUseCase,
) : ViewModel() {

    var articles by mutableStateOf<List<News>>(emptyList())
        private set

    var blogs by mutableStateOf<List<News>>(emptyList())
        private set

    var reports by mutableStateOf<List<News>>(emptyList())
        private set

    var isLoadingArticle by mutableStateOf(false)
        private set

    var isLoadingBlog by mutableStateOf(false)
        private set

    var isLoadingReport by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadArticles()
        loadBlogs()
        loadReports()
    }

    private fun loadArticles(search: String? = null) {
        viewModelScope.launch {
            getArticlesUseCase.invoke(search).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        isLoadingArticle = false
                        errorMessage = null
                        articles = result.data
                    }
                    is Resource.Error -> {
                        isLoadingArticle = false
                        errorMessage = result.message
                    }

                    is Resource.Loading -> {
                        isLoadingArticle = true
                    }
                }
            }
        }
    }

    private fun loadBlogs(search: String? = null) {
        viewModelScope.launch {
            getBlogUseCase.invoke(search).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        isLoadingBlog = false
                        errorMessage = null
                        blogs = result.data
                    }
                    is Resource.Error -> {
                        isLoadingBlog = false
                        errorMessage = result.message
                    }

                    is Resource.Loading -> {
                        isLoadingBlog = true
                    }
                }
            }
        }
    }

    private fun loadReports(search: String? = null) {
        viewModelScope.launch {
            getReportUseCase.invoke(search).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        isLoadingReport = false
                        errorMessage = null
                        reports = result.data
                    }
                    is Resource.Error -> {
                        isLoadingReport = false
                        errorMessage = result.message
                    }

                    is Resource.Loading -> {
                        isLoadingReport = true
                    }
                }
            }
        }
    }

    fun clearError() {
        errorMessage = null
    }
}

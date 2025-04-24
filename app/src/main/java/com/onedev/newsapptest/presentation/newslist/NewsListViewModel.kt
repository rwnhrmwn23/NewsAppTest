package com.onedev.newsapptest.presentation.newslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onedev.newsapptest.domain.model.News
import com.onedev.newsapptest.domain.usecase.GetArticleUseCase
import com.onedev.newsapptest.domain.usecase.GetBlogUseCase
import com.onedev.newsapptest.domain.usecase.GetNewsSiteUseCase
import com.onedev.newsapptest.domain.usecase.GetReportUseCase
import com.onedev.newsapptest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticleUseCase,
    private val getBlogUseCase: GetBlogUseCase,
    private val getReportUseCase: GetReportUseCase,
    private val getNewsSiteUseCase: GetNewsSiteUseCase,
) : ViewModel() {

    var article by mutableStateOf<List<News>>(emptyList())
        private set

    var blogs by mutableStateOf<List<News>>(emptyList())
        private set

    var reports by mutableStateOf<List<News>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var searchText by mutableStateOf("")
        private set

    fun onSearchTextChange(newText: String) {
        searchText = newText
    }

    var typeNews by mutableStateOf("")

    var newsSites by mutableStateOf<List<String>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private var lastQuery: String? = null

    init {
        loadNews()
        loadNewsSites()
    }

    fun loadNews(search: String? = null, newsSite: String? = null) {
        lastQuery = search
        isLoading = true

        viewModelScope.launch {
            when (typeNews) {
                "Article" -> {
                    getArticlesUseCase.invoke(search, newsSite).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                article = result.data
                                errorMessage = null
                            }

                            is Resource.Error -> {
                                isLoading = false
                                errorMessage = result.message
                            }

                            is Resource.Loading -> {
                                isLoading = true
                            }
                        }
                    }
                }

                "Blog" -> {
                    getBlogUseCase.invoke(search, newsSite).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                blogs = result.data
                                errorMessage = null
                            }

                            is Resource.Error -> {
                                isLoading = false
                                errorMessage = result.message
                            }

                            is Resource.Loading -> {
                                isLoading = true
                            }
                        }
                    }
                }

                else -> {
                    getReportUseCase.invoke(search, newsSite).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                reports = result.data
                                errorMessage = null
                            }

                            is Resource.Error -> {
                                isLoading = false
                                errorMessage = result.message
                            }

                            is Resource.Loading -> {
                                isLoading = true
                            }
                        }
                    }
                }
            }

            isLoading = false
        }
    }

    fun loadNewsSites() {
        viewModelScope.launch {
            getNewsSiteUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        newsSites = result.data.newsSites
                        errorMessage = null
                    }

                    is Resource.Error -> {
                        isLoading = false
                        errorMessage = result.message
                    }

                    is Resource.Loading -> {
                        isLoading = true
                    }
                }
            }
        }
    }

    fun clearError() {
        errorMessage = null
    }
}


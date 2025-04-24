package com.onedev.newsapptest.presentation.newslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onedev.newsapptest.domain.model.News
import com.onedev.newsapptest.domain.usecase.GetArticleUseCase
import com.onedev.newsapptest.domain.usecase.GetBlogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticleUseCase,
    private val getBlogUseCase: GetBlogUseCase
) : ViewModel() {

    var article by mutableStateOf<List<News>>(emptyList())
        private set

    var blogs by mutableStateOf<List<News>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var searchText by mutableStateOf("")
        private set

    fun onSearchTextChange(newText: String) {
        searchText = newText
    }

    var typeNews by mutableStateOf("")

    init {
        loadNews()
    }

    private var lastQuery: String? = null

    fun loadNews(search: String? = null) {
        if (typeNews == "Article") {
            if (search == lastQuery && article.isNotEmpty()) return
            lastQuery = search

            viewModelScope.launch {
                isLoading = true
                article = getArticlesUseCase(search)
                isLoading = false
            }
        } else {
            if (search == lastQuery && blogs.isNotEmpty()) return
            lastQuery = search

            viewModelScope.launch {
                isLoading = true
                blogs = getBlogUseCase(search)
                isLoading = false
            }
        }

    }
}

package com.onedev.newsapptest.presentation.newslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onedev.newsapptest.domain.model.Article
import com.onedev.newsapptest.domain.usecase.GetArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticleUseCase
) : ViewModel() {

    var articles by mutableStateOf<List<Article>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var searchText by mutableStateOf("")
        private set

    fun onSearchTextChange(newText: String) {
        searchText = newText
    }


    init {
        loadArticles()
    }

    private var lastQuery: String? = null

    fun loadArticles(search: String? = null) {
        if (search == lastQuery && articles.isNotEmpty()) return
        lastQuery = search

        viewModelScope.launch {
            isLoading = true
            articles = getArticlesUseCase(search)
            isLoading = false
        }
    }
}

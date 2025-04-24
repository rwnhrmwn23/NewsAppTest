package com.onedev.newsapptest.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.onedev.newsapptest.domain.model.Article
import com.onedev.newsapptest.domain.usecase.GetArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticleUseCase
) : ViewModel() {

    var articles by mutableStateOf<List<Article>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            try {
                isLoading = true
                articles = getArticlesUseCase()
            } finally {
                isLoading = false
            }
        }
    }
}

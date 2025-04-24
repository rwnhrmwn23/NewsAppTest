package com.onedev.newsapptest.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail")
    object ArticleList : Screen("articleList/{category}") {
        fun createRoute(category: String) = "articleList/$category"
    }
}
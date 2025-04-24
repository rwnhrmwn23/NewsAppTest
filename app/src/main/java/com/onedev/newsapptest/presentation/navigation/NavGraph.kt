package com.onedev.newsapptest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.onedev.newsapptest.domain.model.Article
import com.onedev.newsapptest.presentation.detail.DetailScreen
import com.onedev.newsapptest.presentation.home.HomeScreen
import com.onedev.newsapptest.utils.getGreeting

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                greeting = getGreeting(),
                userName = "One",
                onArticleClick = { article ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                    navController.navigate(Screen.Detail.route)
                }
            )
        }

        composable(route = Screen.Detail.route) {
            val article = navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")
            article?.let { DetailScreen(it, onBackClick = { navController.popBackStack() }) }
        }
    }
}

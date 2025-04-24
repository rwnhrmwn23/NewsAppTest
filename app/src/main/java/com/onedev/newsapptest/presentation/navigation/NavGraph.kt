package com.onedev.newsapptest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.onedev.newsapptest.domain.model.Article
import com.onedev.newsapptest.presentation.detail.DetailScreen
import com.onedev.newsapptest.presentation.home.HomeScreen
import com.onedev.newsapptest.presentation.newslist.ArticleListViewModel
import com.onedev.newsapptest.presentation.newslist.NewsListScreen
import com.onedev.newsapptest.utils.getGreeting

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                greeting = getGreeting(),
                userName = "One",
                navController = navController,
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

        composable(
            route = Screen.ArticleList.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val category = navBackStackEntry.arguments?.getString("category") ?: "article"
            val viewModel: ArticleListViewModel = hiltViewModel()

            LaunchedEffect(true) {
                if (viewModel.articles.isEmpty()) {
                    viewModel.loadArticles()
                }
            }

            NewsListScreen(
                title = category,
                viewModel = viewModel,
                onClick = { article ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                    navController.navigate(Screen.Detail.route)
                },
                onFilterClick = { /* TODO */ },
                onSortClick = { /* TODO */ }
            )
        }

    }
}

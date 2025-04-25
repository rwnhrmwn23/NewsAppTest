package com.onedev.newsapptest.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.onedev.newsapptest.domain.model.News
import com.onedev.newsapptest.presentation.auth.AuthScreen
import com.onedev.newsapptest.presentation.auth.EntryPointScreen
import com.onedev.newsapptest.presentation.detail.DetailScreen
import com.onedev.newsapptest.presentation.home.HomeScreen
import com.onedev.newsapptest.presentation.newslist.ArticleListViewModel
import com.onedev.newsapptest.presentation.newslist.NewsListScreen
import com.onedev.newsapptest.utils.UserPreferencesManager
import com.onedev.newsapptest.utils.getGreeting

@Composable
fun AppNavGraph(navController: NavHostController, context: Context) {
    NavHost(navController, startDestination = Screen.Entry.route) {
        composable(Screen.Entry.route) {
            EntryPointScreen(context, navController)
        }

        composable(Screen.Auth.route) {
            AuthScreen(
                context = context,
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Home.route) {
            val userName by UserPreferencesManager.getUserName(context).collectAsState(initial = "User")
            HomeScreen(
                greeting = getGreeting(),
                userName = userName,
                navController = navController,
                onClick = { news: News, type: String ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("type", type)
                    navController.currentBackStackEntry?.savedStateHandle?.set("news", news)
                    navController.navigate(Screen.Detail.route)
                },
            )
        }

        composable(route = Screen.Detail.route) {
            val type = navController.previousBackStackEntry?.savedStateHandle?.get<String>("type")
            val news = navController.previousBackStackEntry?.savedStateHandle?.get<News>("news")
            news?.let { data ->
                DetailScreen(
                    news = data,
                    type = type ?: "Article",
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = Screen.ArticleList.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val category = navBackStackEntry.arguments?.getString("category") ?: "article"
            val viewModel: ArticleListViewModel = hiltViewModel()

            LaunchedEffect(true) {
                viewModel.loadNews()
            }

            NewsListScreen(
                title = category,
                viewModel = viewModel,
                onClickNews = { news ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("news", news)
                    navController.navigate(Screen.Detail.route)
                },
                onSortClick = { /* TODO */ }
            )
        }

    }
}

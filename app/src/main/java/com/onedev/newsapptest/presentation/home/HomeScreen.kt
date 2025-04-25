package com.onedev.newsapptest.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.onedev.newsapptest.domain.model.News
import com.onedev.newsapptest.presentation.navigation.Screen

@Composable
fun HomeScreen(
    greeting: String,
    userName: String,
    navController: NavHostController,
    onClick: (News, String) -> Unit?,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val articles = viewModel.articles
    val isLoadingArticle = viewModel.isLoadingArticle
    val blogs = viewModel.blogs
    val isLoadingBlog = viewModel.isLoadingBlog
    val reports = viewModel.reports
    val isLoadingReport = viewModel.isLoadingReport
    val errorMessage = viewModel.errorMessage

    if (errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = errorMessage,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = greeting,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            SectionRow(title = "Article", onSeeMore = {
                navController.navigate(Screen.ArticleList.createRoute("Article"))
            })

            if (isLoadingArticle) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow {
                    items(articles) { article ->
                        NewsItem(news = article, onClick = {
                            onClick(article, "Article")
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            SectionRow(title = "Blog", onSeeMore = {
                navController.navigate(Screen.ArticleList.createRoute("Blog"))
            })

            if (isLoadingBlog) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow {
                    items(blogs) { blogs ->
                        NewsItem(news = blogs, onClick = {
                            onClick(blogs, "Blog")
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            SectionRow(title = "Report", onSeeMore = {
                navController.navigate(Screen.ArticleList.createRoute("Report"))
            })

            if (isLoadingReport) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow {
                    items(reports) { report ->
                        NewsItem(news = report, onClick = {
                            onClick(report, "Report")
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun SectionRow(title: String, onSeeMore: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(
            text = "See more",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.clickable { onSeeMore() }
        )
    }
}

@Composable
fun NewsItem(news: News, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .size(140.dp)
            .clickable { onClick() }
    ) {
        Column {
            AsyncImage(
                model = news.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = news.title,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(4.dp),
                maxLines = 2
            )
        }
    }
}
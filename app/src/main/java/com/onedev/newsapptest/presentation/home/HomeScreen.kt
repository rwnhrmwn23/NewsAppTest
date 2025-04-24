package com.onedev.newsapptest.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.onedev.newsapptest.domain.model.Article

@Composable
fun HomeScreen(
    greeting: String,
    userName: String,
    onArticleClick: (Article) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val articles = viewModel.articles
    val isLoading = viewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
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

        SectionRow(title = "Article", onSeeMore = {})
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyRow {
                items(articles) { article ->
                    ArticleItem(article = article, onClick = {
                        onArticleClick(article)
                    })
                }
            }
        }
    }
}

@Composable
fun SectionRow(title: String, onSeeMore: () -> Unit) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth(),
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
fun ArticleItem(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(end = 8.dp)
            .size(140.dp)
            .clickable { onClick() }
    ) {
        Column {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = article.title,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(4.dp),
                maxLines = 2
            )
        }
    }
}
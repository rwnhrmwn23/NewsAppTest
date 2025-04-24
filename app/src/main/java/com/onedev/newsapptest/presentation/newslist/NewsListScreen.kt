package com.onedev.newsapptest.presentation.newslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.onedev.newsapptest.domain.model.News
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(FlowPreview::class)
@Composable
fun NewsListScreen(
    title: String,
    viewModel: ArticleListViewModel,
    onClickNews: (News) -> Unit,
    onSortClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    val search by remember { derivedStateOf { viewModel.searchText } }
    val articles by remember { derivedStateOf { viewModel.article } }
    val blogs by remember { derivedStateOf { viewModel.blogs } }
    val reports by remember { derivedStateOf { viewModel.reports } }
    val isLoading by remember { derivedStateOf { viewModel.isLoading } }

    viewModel.typeNews = title

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp),
                value = search,
                onValueChange = { viewModel.onSearchTextChange(it) },
                placeholder = { Text("Search") },
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon")
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5)
                )
            )
            IconButton(onClick = onFilterClick) {
                Icon(Icons.Default.FilterList, contentDescription = "Filter")
            }
            IconButton(onClick = onSortClick) {
                Icon(Icons.AutoMirrored.Default.Sort, contentDescription = "Sort")
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            when (viewModel.typeNews) {
                "Article" -> {
                    if (articles.isEmpty()) {
                        EmptyStateMessage(viewModel.typeNews)
                    } else {
                        LazyColumn {
                            items(articles) { article ->
                                NewsListItem(news = article, onClick = { onClickNews(article) })
                            }
                        }
                    }
                }

                "Blog" -> {
                    if (blogs.isEmpty()) {
                        EmptyStateMessage(viewModel.typeNews)
                    } else {
                        LazyColumn {
                            items(blogs) { blog ->
                                NewsListItem(news = blog, onClick = { onClickNews(blog) })
                            }
                        }
                    }
                }

                else -> {
                    if (reports.isEmpty()) {
                        EmptyStateMessage(viewModel.typeNews)
                    } else {
                        LazyColumn {
                            items(reports) { report ->
                                NewsListItem(news = report, onClick = { onClickNews(report) })
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { viewModel.searchText }
            .debounce(800)
            .distinctUntilChanged()
            .collectLatest { query ->
                when (viewModel.typeNews) {
                    "Article" -> {
                        if (viewModel.article.isEmpty() || query.isNotEmpty()) {
                            viewModel.loadNews(search = query)
                        }
                    }

                    "Blog" -> {
                        if (viewModel.blogs.isEmpty() || query.isNotEmpty()) {
                            viewModel.loadNews(search = query)
                        }
                    }

                    else -> {
                        if (viewModel.reports.isEmpty() || query.isNotEmpty()) {
                            viewModel.loadNews(search = query)
                        }
                    }
                }
            }
    }
}

@Composable
fun EmptyStateMessage(type: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No $type Found",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}


@Composable
fun NewsListItem(news: News, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
    ) {
        Column {

            AsyncImage(
                model = news.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = news.title,
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Launches", style = MaterialTheme.typography.bodySmall)
                    if (news.launches.isNullOrEmpty()) {
                        Text("-", style = MaterialTheme.typography.bodySmall)
                    } else {
                        news.launches.forEach {
                            OutlinedButton(onClick = {}) {
                                Text(it.provider)
                            }
                        }
                    }
                }
                Column {
                    Text("Event", style = MaterialTheme.typography.bodySmall)
                    if (news.events.isNullOrEmpty()) {
                        Text("-", style = MaterialTheme.typography.bodySmall)
                    } else {
                        news.events.forEach {
                            OutlinedButton(onClick = {}) {
                                Text(it.provider)
                            }
                        }
                    }
                }
            }
        }
    }
}

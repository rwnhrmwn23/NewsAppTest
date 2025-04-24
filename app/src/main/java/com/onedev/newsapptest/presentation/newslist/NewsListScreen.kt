package com.onedev.newsapptest.presentation.newslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.onedev.newsapptest.domain.model.Article
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(FlowPreview::class)
@Composable
fun NewsListScreen(
    title: String,
    viewModel: ArticleListViewModel,
    onClick: (Article) -> Unit,
    onSortClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    val search by remember { derivedStateOf { viewModel.searchText } }
    val articles by remember { derivedStateOf { viewModel.articles } }
    val isLoading by remember { derivedStateOf { viewModel.isLoading } }

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
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn {
                items(articles) { article ->
                    ArticleListItem(article = article, onClick = { onClick(article) })
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { viewModel.searchText }
            .debounce(800)
            .distinctUntilChanged()
            .collectLatest { query ->
                if (viewModel.articles.isEmpty() || query.isNotEmpty()) {
                    viewModel.loadArticles(search = query)
                }
            }
    }
}


@Composable
fun ArticleListItem(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
    ) {
        Column {

            AsyncImage(
                model = article.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = article.title,
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
                    if (article.launches.isEmpty()) {
                        Text("-", style = MaterialTheme.typography.bodySmall)
                    } else {
                        article.launches.forEach {
                            OutlinedButton(onClick = {}) {
                                Text(it.provider)
                            }
                        }
                    }
                }
                Column {
                    Text("Event", style = MaterialTheme.typography.bodySmall)
                    if (article.events.isEmpty()) {
                        Text("-", style = MaterialTheme.typography.bodySmall)
                    } else {
                        article.events.forEach {
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


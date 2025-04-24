package com.onedev.newsapptest.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.onedev.newsapptest.domain.model.News
import com.onedev.newsapptest.utils.extractSummary
import com.onedev.newsapptest.utils.toFormattedDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    news: News,
    type: String,
    onBackClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box {
            AsyncImage(
                model = news.imageUrl,
                contentDescription = news.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            TopAppBar(
                title = { Text("Detail $type") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                modifier = Modifier
                    .statusBarsPadding()
                    .background(Color.Transparent)
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = news.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Published at: ${news.publishedAt.toFormattedDate()}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = news.summary.extractSummary(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
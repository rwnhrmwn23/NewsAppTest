package com.onedev.newsapptest.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onedev.newsapptest.utils.getGreeting

@Composable
fun HomeScreen(
    userName: String = "User Name",
    greeting: String = getGreeting(),
    onSeeMoreClick: (String) -> Unit = {},
    onItemClick: (String, Int) -> Unit = { _, _ -> }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = greeting,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = userName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SectionRow(title = "Article", onSeeMore = { onSeeMoreClick("Article") })
        ItemRow(section = "article", onItemClick = onItemClick)

        SectionRow(title = "Blog", onSeeMore = { onSeeMoreClick("Blog") })
        ItemRow(section = "blog", onItemClick = onItemClick)

        SectionRow(title = "Report", onSeeMore = { onSeeMoreClick("Report") })
        ItemRow(section = "report", onItemClick = onItemClick)
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
fun ItemRow(section: String, onItemClick: (String, Int) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        items(3) { index ->
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(100.dp)
                    .background(Color.LightGray)
                    .clickable { onItemClick(section, index) }
            )
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        userName = "One",
        onSeeMoreClick = { section -> println("See more: $section") },
        onItemClick = { section, index -> println("Clicked $section item $index") }
    )
}

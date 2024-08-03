package com.example.testapp.Screens

import BookmarkedViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.testapp.presentation.news.NewsItem

@Composable
fun BookmarkScreen(viewModel: BookmarkedViewModel, searchQuery: String) {
    val bookmarkedArticles by viewModel.bookmarkedArticles.collectAsState()

    val filteredArticles = bookmarkedArticles.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.description?.contains(searchQuery, ignoreCase = true) == true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        LazyColumn {
            items(filteredArticles) { article ->
                NewsItem(
                    title = article.title,
                    description = article.description,
                    url = article.url,
                    imageUrl = article.imageUrl,
                    viewModel = viewModel
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
            }
        }
    }
}

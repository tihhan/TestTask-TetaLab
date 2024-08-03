package com.example.testapp.presentation.news

import BookmarkedViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testapp.ViewModels.NewsViewModel

@Composable
fun NewsApp(BookmarkedViewModel: BookmarkedViewModel, viewModel: NewsViewModel, searchQuery: String) {
    val filteredNewsList by remember(searchQuery) {
        derivedStateOf {
            viewModel.newsList.filter { it.title.contains(searchQuery, ignoreCase = true) }
        }
    }
    val listState = rememberLazyListState()
    val loadingState by viewModel.loadingState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index >= filteredNewsList.size - 1 && !loadingState) {
                    viewModel.loadMoreNews()
                }
            }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.LightGray)
        .padding(16.dp)) {

        if (loadingState) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        errorState?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(state = listState) {
            items(filteredNewsList.size) { index ->
                val article = filteredNewsList[index]
                NewsItem(
                    title = article.title,
                    description = article.description,
                    url = article.url,
                    imageUrl = article.urlToImage,
                    viewModel = BookmarkedViewModel
                )
                Spacer(modifier = Modifier.height(6.dp))
                Divider()
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}


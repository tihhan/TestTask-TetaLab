package com.example.testapp.presentation.tabview

import BookmarkedViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.testapp.Screens.BookmarkScreen
import com.example.testapp.ViewModels.NewsViewModel
import com.example.testapp.presentation.news.NewsApp
import com.example.testapp.presentation.search.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTabView(bookmarkedViewModel: BookmarkedViewModel, viewModel: NewsViewModel) {
    val tabTitles = listOf("Новини", "Обрані")
    val selectedTabIndex = rememberSaveable { mutableStateOf(0) }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column {
                SearchBar(searchQuery, onSearchQueryChange = { searchQuery = it })

                TabRow(
                    selectedTabIndex = selectedTabIndex.value,
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
                            color = Color.Black
                        )
                    }
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex.value == index,
                            onClick = { selectedTabIndex.value = index },
                            text = { Text(text = title) }
                        )
                    }
                }
            }
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                when (selectedTabIndex.value) {
                    0 -> NewsApp(BookmarkedViewModel=bookmarkedViewModel,viewModel = viewModel, searchQuery = searchQuery)
                    1 -> BookmarkScreen(viewModel = bookmarkedViewModel, searchQuery = searchQuery)
                }
            }
        }
    )
}

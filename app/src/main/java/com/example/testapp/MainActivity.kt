package com.example.testapp

import BookmarkedViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.testapp.ViewModels.NewsViewModel
import com.example.testapp.presentation.tabview.MyTabView


class MainActivity : ComponentActivity() {
    private val viewModel: NewsViewModel by viewModels()
    private val bookmarkedViewModel: BookmarkedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTabView(bookmarkedViewModel = bookmarkedViewModel, viewModel = viewModel)
        }
    }
}








package com.example.testapp.data.Network

data class Article(
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val author: String?,  // New field
    val publishedAt: String?
)

package com.example.testapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_articles")
data class BookmarkedArticle(
    @PrimaryKey val url: String,
    val title: String,
    val description: String?,
    val imageUrl: String?
)
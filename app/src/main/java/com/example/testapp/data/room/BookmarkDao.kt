package com.example.testapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert
    suspend fun insertArticle(article: BookmarkedArticle)

    @Query("DELETE FROM bookmarked_articles WHERE url = :url")
    suspend fun deleteArticle(url: String)

    @Query("SELECT * FROM bookmarked_articles")
    fun getAllBookmarkedArticles(): Flow<List<BookmarkedArticle>>
}

package com.example.testapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: BookmarkedArticle)

    @Delete
    suspend fun delete(article: BookmarkedArticle)

    @Query("SELECT * FROM bookmarked_articles")
    fun getAllBookmarkedArticles(): Flow<List<BookmarkedArticle>>
}
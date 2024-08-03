package com.example.testapp.data.room

import kotlinx.coroutines.flow.Flow

class BookmarkRepository(private val bookmarkDao: BookmarkDao) {
    fun getAllBookmarkedArticles(): Flow<List<BookmarkedArticle>> = bookmarkDao.getAllBookmarkedArticles()

    suspend fun insertArticle(article: BookmarkedArticle) {
        bookmarkDao.insert(article)
    }

    suspend fun deleteArticle(article: BookmarkedArticle) {
        bookmarkDao.delete(article)
    }
}

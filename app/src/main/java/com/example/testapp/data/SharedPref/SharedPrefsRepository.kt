package com.example.testapp.data.SharedPref

import android.content.Context

class SharedPrefsRepository(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("bookmarks", Context.MODE_PRIVATE)

    fun isBookmarked(url: String): Boolean {
        return sharedPreferences.getBoolean(url, false)
    }

    fun toggleBookmark(url: String) {
        val isBookmarked = !isBookmarked(url)
        sharedPreferences.edit().putBoolean(url, isBookmarked).apply()
    }

    fun getAllBookmarkedUrls(): Set<String> {
        return sharedPreferences.all.keys
    }
}

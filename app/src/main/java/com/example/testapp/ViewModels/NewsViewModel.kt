package com.example.testapp.ViewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.Network.Article
import com.example.testapp.data.Network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class NewsViewModel : ViewModel() {

    private val _newsList = mutableStateListOf<Article>()
    val newsList: List<Article> get() = _newsList

    private var currentPage = 1
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    init {
        fetchNews()
    }

    private fun fetchNews() {
        _loadingState.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTopHeadlines("ua", "09de2de54bcd454e9741a27124953c6d", currentPage)
                _newsList.addAll(response.articles)
                _errorState.value = null
            } catch (e: IOException) {
                _errorState.value = "Network error. Please try again."
            } catch (e: HttpException) {
                _errorState.value = "Error fetching news. Please try again."
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun loadMoreNews() {
        if (_loadingState.value) return
        currentPage++
        fetchNews()
    }

}

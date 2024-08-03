import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.Network.Article
import com.example.testapp.data.room.AppDatabase
import com.example.testapp.data.room.BookmarkRepository
import com.example.testapp.data.room.BookmarkedArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookmarkedViewModel(application: Application) : AndroidViewModel(application) {
    private val bookmarkDao = AppDatabase.getDatabase(application).bookmarkDao()
    private val repository = BookmarkRepository(bookmarkDao)

    private val _bookmarkedArticles = MutableStateFlow<List<BookmarkedArticle>>(emptyList())
    val bookmarkedArticles: StateFlow<List<BookmarkedArticle>> = _bookmarkedArticles.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllBookmarkedArticles().collect { articles ->
                _bookmarkedArticles.value = articles
            }
        }
    }

    fun toggleBookmark(article: Article) {
        val bookmarkedArticle = BookmarkedArticle(
            url = article.url,
            title = article.title,
            description = article.description,
            imageUrl = article.urlToImage
        )

        viewModelScope.launch {
            if (_bookmarkedArticles.value.any { it.url == article.url }) {
                repository.deleteArticle(bookmarkedArticle)
            } else {
                repository.insertArticle(bookmarkedArticle)
            }
        }
    }
}

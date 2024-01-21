package eu.tutorials.bookfinder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.bookfinder.network.Book
import eu.tutorials.bookfinder.network.BookFinderApi
import kotlinx.coroutines.launch

enum class BookApiStatus { LOADING, ERROR, DONE, EMPTY }
class BookViewModel() : ViewModel() {

    private val _status = MutableLiveData(BookApiStatus.EMPTY)
    val status: LiveData<BookApiStatus> = _status

//display the total number of books or the exception

    private val _response = MutableLiveData<String>()
    val response: LiveData<String> = _response

//take the query from the SearchView, that the user fills in

    private var _query = MutableLiveData("")
    val query: LiveData<String> = _query

//hold the data of all the books received from the Google Books API

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    fun setQuery(query: String) {
        _query.value = query
        searchBooks()
    }

    //method that updates the _books property with a empty array list then checks if _query list is not empty and calls getbooks
    private fun searchBooks() {
        _books.value = ArrayList()
        if (_query.value != "") {
            getBooks()
        }
    }

    fun resetStatus() {
        _status.value = BookApiStatus.EMPTY
        _response.value = ""
        _books.value = ArrayList()
    }

    private fun getBooks() {
        _status.value = BookApiStatus.LOADING
        viewModelScope.launch {
            try {
                val response =
                    BookFinderApi.retrofitService.getDetails(
                        searchString = _query.value!!,
                        maxResults = "10",
                        printType = "books"
                    )
                _response.value = "10 out of ${response.totalItems} books found."
                _books.value = response.items
                _status.value = BookApiStatus.DONE
                Log.d("BVM", "VModel${_status.value} ${_books.value?.size}")
            } catch (e: Exception) {
                _status.value = BookApiStatus.ERROR
                _books.value = ArrayList()
                _response.value = "Error:${e.message}"
                Log.d("BVM", "getBooks:${e.message}")
            }
        }
    }
}

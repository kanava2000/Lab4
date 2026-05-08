package com.example.lab3.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lab3.data.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookViewModel : ViewModel() {

    private val _books = MutableStateFlow(
        listOf(
            Book(1, "Война и мир", "Лев Толстой", 1869, 5f),
            Book(2, "1984", "Джордж Оруелл", 1949, 4f),
            Book(3, "Над прірвою", "Джей Сінгер", 1951, 3f)
        )
    )
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private var nextId = 4

    fun getBookById(id: Int): Book? = _books.value.find { it.id == id }

    fun addBook(title: String, author: String, year: Int) {
        val newBook = Book(
            id = nextId++,
            title = title,
            author = author,
            year = year,
            rating = 0f
        )
        _books.value = _books.value + newBook
    }

    fun updateBook(id: Int, title: String, author: String, year: Int) {
        _books.value = _books.value.map { book ->
            if (book.id == id) book.copy(title = title, author = author, year = year)
            else book
        }
    }

    fun updateRating(id: Int, rating: Float) {
        _books.value = _books.value.map { book ->
            if (book.id == id) book.copy(rating = rating) else book
        }
    }

    fun deleteBook(id: Int) {
        _books.value = _books.value.filter { it.id != id }
    }
}
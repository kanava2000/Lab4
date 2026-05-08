package com.example.lab3.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab3.data.Book
import com.example.lab3.viewmodel.BookViewModel

val Purple = Color(0xFF9C27B0)
val LightPurple = Color(0xFFE1BEE7)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksListScreen(
    viewModel: BookViewModel,
    onBookClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    val books by viewModel.books.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Моя бібліотека",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Purple,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Додати книгу")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(books) { book ->
                BookItem(book = book, onClick = { onBookClick(book.id) })
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Кольоровий прямокутник замість обкладинки
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(56.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(containerColor = Purple),
                    shape = RoundedCornerShape(4.dp)
                ) {}
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = book.author,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                StarRating(rating = book.rating)
            }
        }
    }
}

@Composable
fun StarRating(rating: Float, maxStars: Int = 5) {
    Row {
        for (i in 1..maxStars) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (i <= rating) Color(0xFFFFC107) else Color(0xFFBDBDBD),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
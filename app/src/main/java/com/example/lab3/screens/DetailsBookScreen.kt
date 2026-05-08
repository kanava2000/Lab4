package com.example.lab3.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab3.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsBookScreen(
    viewModel: BookViewModel,
    bookId: Int,
    onBack: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: () -> Unit
) {
    val books by viewModel.books.collectAsState()
    val book = books.find { it.id == bookId }

    if (book == null) {
        onBack()
        return
    }

    var currentRating by remember { mutableFloatStateOf(book.rating) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Деталі книги", color = Color.White, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Іконка книги
            Icon(
                imageVector = Icons.Default.Book,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = Purple
            )

            Text(
                text = book.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = book.author,
                fontSize = 16.sp,
                color = Purple
            )

            Divider()

            // Рік видання
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Рік видання", color = Color.Gray, fontSize = 13.sp)
                    Text(
                        text = book.year.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Рейтинг
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Ваша оцінка", color = Color.Gray, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Зірки з кількістю
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        for (i in 1..5) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = if (i <= currentRating) Color(0xFFFFC107) else Color(0xFFBDBDBD),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("(${currentRating.toInt()})", color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Слайдер
                    Slider(
                        value = currentRating,
                        onValueChange = { newVal ->
                            currentRating = newVal
                            viewModel.updateRating(bookId, newVal)
                        },
                        valueRange = 0f..5f,
                        steps = 4,
                        colors = SliderDefaults.colors(
                            thumbColor = Purple,
                            activeTrackColor = Purple
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Кнопки
            Button(
                onClick = { onEdit(bookId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Purple),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Редагувати", color = Color.White, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = {
                    viewModel.deleteBook(bookId)
                    onDelete()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
            ) {
                Text("Видалити", fontWeight = FontWeight.Bold)
            }
        }
    }
}
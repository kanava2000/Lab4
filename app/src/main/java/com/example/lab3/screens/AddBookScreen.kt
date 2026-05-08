package com.example.lab3.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.lab3.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    viewModel: BookViewModel,
    bookId: Int?,
    onDone: () -> Unit,
    onCancel: () -> Unit
) {
    val existingBook = bookId?.let { viewModel.getBookById(it) }

    var title by remember { mutableStateOf(existingBook?.title ?: "") }
    var author by remember { mutableStateOf(existingBook?.author ?: "") }
    var year by remember { mutableStateOf(existingBook?.year?.toString() ?: "") }

    val isEdit = existingBook != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isEdit) "Редагувати книгу" else "Нова книга",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Назва книги") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Автор") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Рік видання") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val yearInt = year.toIntOrNull() ?: 0
                    if (title.isNotBlank() && author.isNotBlank()) {
                        if (isEdit) {
                            viewModel.updateBook(bookId!!, title, author, yearInt)
                        } else {
                            viewModel.addBook(title, author, yearInt)
                        }
                        onDone()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Purple),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(if (isEdit) "Зберегти" else "Додати", color = Color.White, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Purple)
            ) {
                Text("Скасувати", fontWeight = FontWeight.Bold)
            }
        }
    }
}
package com.example.lab3.data

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val year: Int,
    val rating: Float = 0f
)
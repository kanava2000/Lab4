package com.example.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.lab3.navigation.NavGraph
import com.example.lab3.ui.theme.Lab3Theme
import com.example.lab3.viewmodel.BookViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab3Theme {
                val navController = rememberNavController()
                val viewModel: BookViewModel = viewModel()
                NavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}
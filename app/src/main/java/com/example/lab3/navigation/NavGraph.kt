package com.example.lab3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lab3.screens.AddBookScreen
import com.example.lab3.screens.BooksListScreen
import com.example.lab3.screens.DetailsBookScreen
import com.example.lab3.viewmodel.BookViewModel

sealed class Screen(val route: String) {
    object BooksList : Screen("books_list")
    object AddBook : Screen("add_book")
    object EditBook : Screen("edit_book/{bookId}") {
        fun createRoute(bookId: Int) = "edit_book/$bookId"
    }
    object Details : Screen("details/{bookId}") {
        fun createRoute(bookId: Int) = "details/$bookId"
    }
}

@Composable
fun NavGraph(navController: NavHostController, viewModel: BookViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.BooksList.route
    ) {
        composable(Screen.BooksList.route) {
            BooksListScreen(
                viewModel = viewModel,
                onBookClick = { bookId ->
                    navController.navigate(Screen.Details.createRoute(bookId))
                },
                onAddClick = {
                    navController.navigate(Screen.AddBook.route)
                }
            )
        }

        composable(Screen.AddBook.route) {
            AddBookScreen(
                viewModel = viewModel,
                bookId = null,
                onDone = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditBook.route,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId")
            AddBookScreen(
                viewModel = viewModel,
                bookId = bookId,
                onDone = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: return@composable
            DetailsBookScreen(
                viewModel = viewModel,
                bookId = bookId,
                onBack = { navController.popBackStack() },
                onEdit = { id ->
                    navController.navigate(Screen.EditBook.createRoute(id))
                },
                onDelete = { navController.popBackStack() }
            )
        }
    }
}
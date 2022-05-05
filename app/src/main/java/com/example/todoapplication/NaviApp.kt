package com.example.todoapplication

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapplication.create.CreateToDoScreen
import com.example.todoapplication.create.CreateViewModel
import com.example.todoapplication.edit.EditScreen
import com.example.todoapplication.edit.EditViewModel
import com.example.todoapplication.main.MainScreen
import com.example.todoapplication.main.MainScreenViewModel

@Composable
fun NaviApp() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            val model = hiltViewModel<MainScreenViewModel>()
            MainScreen(navController = navController, model = model)
        }
        composable("create") {
            val model = hiltViewModel<CreateViewModel>()
            CreateToDoScreen(navController = navController, model = model)
        }
        composable("edit/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.IntType })) { backStackEntry ->
            val model = hiltViewModel<EditViewModel>()
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: 0
            model.setId(todoId)
            EditScreen(navController = navController, model = model)
        }
    }
}

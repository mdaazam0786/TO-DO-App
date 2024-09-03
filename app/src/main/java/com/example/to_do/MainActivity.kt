package com.example.to_do

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.to_do.ui.theme.ToDoTheme
import com.example.to_do.ui.theme.add_todo.AddTodoScreen
import com.example.to_do.ui.theme.detail_todo.DetailTodoScreen
import com.example.to_do.ui.theme.edit_todo.EditTodoScreen
import com.example.to_do.ui.theme.todo_list.HomeScreen
import com.example.to_do.ui.theme.todo_list.HomeScreenViewModel
import com.example.to_do.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.TODO_LIST
                ) {
                    composable(Routes.TODO_LIST) {
                        HomeScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            },
                        )
                    }
                    composable(
                        route = Routes.ADD_TODO + "?todoId={todoId}",
                        arguments = listOf(
                            navArgument(name = "todoId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddTodoScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                    composable(
                        route = Routes.DETAIL_TODO + "?todoId={todoId}",
                        arguments = listOf(
                            navArgument(name = "todoId"){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ){
                        DetailTodoScreen(onPopBackStack = { navController.popBackStack() }, onNavigate = {
                            navController.navigate(it.route)
                        })
                    }
                    composable(
                        route = Routes.EDIT_TODO + "?todoId={todoId}",
                        arguments = listOf(
                            navArgument(name = "todoId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ){
                        EditTodoScreen(onNavigate =  {
                            navController.navigate(it.route)
                        })
                    }

                }
            }
        }
    }
}



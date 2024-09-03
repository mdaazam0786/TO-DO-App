package com.example.to_do.ui.theme.todo_list

import com.example.to_do.data.Todo


sealed class HomeScreenEvent {
    data class OnTodoClick(val todo: Todo): HomeScreenEvent()
    object OnAddTodoClick: HomeScreenEvent()
}

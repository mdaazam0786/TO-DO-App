package com.example.to_do.util

import com.example.to_do.data.Todo

data class TodoState (
    val todos: List<Todo> = emptyList(),
    val title : String = "",
    val description : String ?= "",
    val isDone : Boolean = false,
    val isDeletingTodo : Boolean = false,
)
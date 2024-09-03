package com.example.to_do.data

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.Date

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun updateTodo(todo : Todo)

    suspend fun getTodoById(id: Int): Todo?

    fun getTodos(): Flow<List<Todo>>
}
package com.example.to_do.data

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class TodoRepositoryImpl(
    private val dao: TodoDao
): TodoRepository {

    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override suspend fun updateTodo(todo: Todo) {
        dao.updateTodo(todo)
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)
    }

    override fun getTodos(): Flow<List<Todo>> {
        return dao.getTodos()
    }

}
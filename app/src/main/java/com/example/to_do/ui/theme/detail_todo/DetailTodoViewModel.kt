package com.example.to_do.ui.theme.detail_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.to_do.data.Todo
import com.example.to_do.data.TodoRepository
import com.example.to_do.util.Routes
import com.example.to_do.util.TodoState
import com.example.to_do.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTodoViewModel @Inject constructor(
    private val repository : TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var _state = MutableStateFlow(TodoState())
    val state = _state.asStateFlow()


    var todo by mutableStateOf<Todo?>(null)
        private set

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTodo: Todo? = null

    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if(todoId != -1){
            viewModelScope.launch {
                todo = repository.getTodoById(todoId)
            }
        }
    }

    fun onDetailEvent(event: DetailScreenEvent){
        when(event){
            is DetailScreenEvent.onDeleteClick -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isDeletingTodo = true
                        )
                    }
                }
            }
            is DetailScreenEvent.onPendingClick -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = !event.isDone
                        )
                    )
                    sendUiEvent(UiEvent.Navigate(Routes.TODO_LIST))
                }
            }
            is DetailScreenEvent.onEditClick -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.EDIT_TODO + "?todoId=${event.todo.id}"))
                }
            }

            is DetailScreenEvent.onHideDialogClick -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isDeletingTodo = false
                        )
                    }
                }
            }
            is DetailScreenEvent.onDeleteDialogClick -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Todo deleted",
                        action = "Undo"
                    ))
                    sendUiEvent(UiEvent.Navigate(Routes.TODO_LIST))
                }
            }

        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
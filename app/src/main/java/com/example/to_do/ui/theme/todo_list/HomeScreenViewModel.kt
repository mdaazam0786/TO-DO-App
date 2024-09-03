package com.example.to_do.ui.theme.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_do.data.TodoRepository
import com.example.to_do.util.UiEvent
import com.example.to_do.util.Routes
import com.example.to_do.util.TodoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel(){

    val todos = repository.getTodos()

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHomeEvent(event: HomeScreenEvent) {
        when(event) {
            is HomeScreenEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.DETAIL_TODO+ "?todoId=${event.todo.id}"))
            }
            is HomeScreenEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_TODO))
            }

        }
    }


    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
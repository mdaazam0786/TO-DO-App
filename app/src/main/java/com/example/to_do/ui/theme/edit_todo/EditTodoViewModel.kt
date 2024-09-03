package com.example.to_do.ui.theme.edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_do.data.Todo
import com.example.to_do.data.TodoRepository
import com.example.to_do.ui.theme.add_todo.AddScreenEvent
import com.example.to_do.util.Routes
import com.example.to_do.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.time.Instant
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {


    private val calendar = Calendar.getInstance().time
    private val dateFormat = DateFormat.getDateInstance().format(calendar)
    var todo by mutableStateOf<Todo?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if(todoId != -1){
            viewModelScope.launch {
                repository.getTodoById(todoId)?.let {todo->
                    title = todo.title
                    description = todo.description ?: ""
                    this@EditTodoViewModel.todo = todo

                }

            }
        }
    }
    fun onEditEvent(event : EditScreenEvent){
        when(event){
            is EditScreenEvent.onTitleChange-> {
                title = event.title
            }
            is EditScreenEvent.onDescChange -> {
                description = event.desc
            }
            is EditScreenEvent.onSaveTodo -> {
                viewModelScope.launch {
                    if(title.isBlank()){
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "The Title can't be Empty"
                        ))
                        return@launch
                    }else{
                        repository.updateTodo(Todo(
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false,
                            date = dateFormat,
                            id = todo?.id
                        ))
                        sendUiEvent(UiEvent.Navigate(Routes.TODO_LIST))
                    }
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
package com.example.to_do.ui.theme.add_todo


sealed class AddScreenEvent {
     data class onTitleChange(val title :String) : AddScreenEvent()
     data class onDescChange(val desc : String) : AddScreenEvent()
     object onSaveTodo : AddScreenEvent()
}
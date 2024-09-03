package com.example.to_do.ui.theme.edit_todo


sealed class EditScreenEvent {
    data class onTitleChange(val title :String) : EditScreenEvent()
    data class onDescChange(val desc : String) : EditScreenEvent()
    object onSaveTodo : EditScreenEvent()
}
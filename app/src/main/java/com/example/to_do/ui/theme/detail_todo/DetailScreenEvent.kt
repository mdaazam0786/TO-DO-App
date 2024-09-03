package com.example.to_do.ui.theme.detail_todo

import com.example.to_do.data.Todo

sealed class DetailScreenEvent {
    object onDeleteClick : DetailScreenEvent()
    data class onPendingClick(val todo : Todo, val isDone : Boolean) : DetailScreenEvent()
    data class onEditClick(val todo : Todo) : DetailScreenEvent()
    data class onDeleteDialogClick(val todo : Todo) : DetailScreenEvent()
    object onHideDialogClick : DetailScreenEvent()
}
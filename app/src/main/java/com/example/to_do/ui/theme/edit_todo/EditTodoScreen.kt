package com.example.to_do.ui.theme.edit_todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.to_do.R
import com.example.to_do.util.UiEvent

@Composable
fun EditTodoScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: EditTodoViewModel = hiltViewModel()
){

    val scaffoldState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{event->
            when(event){

                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }

                is UiEvent.Navigate -> onNavigate(event)

                else ->  Unit
            }

        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffF79E89))
                .padding(it)){
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(value = viewModel.title, onValueChange = {title->
                    viewModel.onEditEvent(EditScreenEvent.onTitleChange(title))
                },
                    placeholder = {
                        Text(text = "Title", color = Color.White, fontFamily = FontFamily(Font(R.font.montserrat_regular)))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(20),

                    )
                OutlinedTextField(value = viewModel.description, onValueChange = {desc->
                    viewModel.onEditEvent(EditScreenEvent.onDescChange(desc))
                },
                    placeholder = {
                        Text(text = "Description", color = Color.White, fontFamily = FontFamily(Font(R.font.montserrat_regular)))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(8.dp),
                    maxLines = 5
                )
                Button(
                    onClick = {
                        viewModel.onEditEvent(EditScreenEvent.onSaveTodo)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "Edit TODO",
                        color = Color(0xffF79E89),
                        fontFamily = FontFamily(Font(R.font.montserrat_regular))
                    )

                }
            }

        }

    }
}
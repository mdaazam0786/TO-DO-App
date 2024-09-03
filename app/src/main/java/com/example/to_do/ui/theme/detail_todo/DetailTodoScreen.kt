package com.example.to_do.ui.theme.detail_todo

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.to_do.R
import com.example.to_do.data.Todo
import com.example.to_do.util.TodoState
import com.example.to_do.util.UiEvent
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTodoScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: DetailTodoViewModel = hiltViewModel(),
){
    val state = viewModel.state.collectAsState()


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{event->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)

                is UiEvent.PopBackStack -> onPopBackStack()

                else -> Unit
            }
        }
    }
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                navigationIcon = { 
                    IconButton(onClick = {
                        onPopBackStack()
                    }, modifier = Modifier.size(50.dp)) {
                        Icon(painter = painterResource(id = R.drawable.left),
                            contentDescription = null )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.todo?.let { DetailScreenEvent.onPendingClick(it, viewModel.todo!!.isDone)}?.let { viewModel.onDetailEvent(it) }
                    }) {
                        Icon(painter = painterResource(id = R.drawable.clock), contentDescription = null)
                    }
                    IconButton(onClick = {
                        viewModel.todo?.let { DetailScreenEvent.onEditClick(it)}?.let { viewModel.onDetailEvent(it) }

                    }) {
                        Icon(painter = painterResource(id = R.drawable.edit) , contentDescription = null)
                    }
                    IconButton(onClick = {
                       viewModel.onDetailEvent(DetailScreenEvent.onDeleteClick)
                    }) {
                        Icon(painter = painterResource(id = R.drawable.delete) , contentDescription = null)
                    }
                }
            )
        },
    ) {
        if(state.value.isDeletingTodo){
            DeleteDialog(onEvent = viewModel::onDetailEvent)
        }
        Box(modifier = Modifier.padding(it)){
            Column(
                modifier = Modifier
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                viewModel.todo?.let { it1 ->
                    Text(
                        text = it1.title,
                        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 34.sp
                    )
                }
                viewModel.todo?.let { it1 ->
                    it1.description?.let { it2 ->
                        Text(
                            text = it2,
                            fontFamily = FontFamily(Font(R.font.montserrat_regular)),

                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DeleteDialog(
    viewModel: DetailTodoViewModel = hiltViewModel(),
    onEvent: (DetailScreenEvent) -> Unit,
){

//        AlertDialog(
//            onDismissRequest = {
//                onEvent(DetailScreenEvent.onHideDialogClick)
//            },
//            confirmButton = {
//               Button(onClick = {
//                   viewModel.todo?.let {
//                       DetailScreenEvent.onDeleteDialogClick(
//                           it
//                       )
//                   }?.let { viewModel.onDetailEvent(it) }
//               }) {
//                   Text(text = "DELETE TODO", color = Color(0xffF76C6A), fontFamily = FontFamily(Font(R.font.montserrat_regular)))
//               }
//            },
//            dismissButton = {
//                Button(onClick = {
//                    viewModel.onDetailEvent(DetailScreenEvent.onHideDialogClick)
//                }) {
//                    Text(text = "CANCEL", color = Color(0xff00FF19), fontFamily = FontFamily(Font(R.font.montserrat_regular)))
//                }
//            },
//            title = { /*TODO*/ },
//        )
    val bottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(onDismissRequest = {
        onEvent(DetailScreenEvent.onHideDialogClick)
                                        },
        sheetState = bottomSheetState) {
        Column {
            Button(onClick = {
                viewModel.todo?.let {
                    DetailScreenEvent.onDeleteDialogClick(
                        it
                    )
                }?.let { viewModel.onDetailEvent(it) }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp), colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                Text(text = "DELETE TODO", color = Color(0xffF76C6A), fontFamily = FontFamily(Font(R.font.montserrat_regular)))
            }
            Button(onClick = {
                viewModel.onDetailEvent(DetailScreenEvent.onHideDialogClick)
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp), colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text(text = "CANCEL", color = Color(0xff00FF19), fontFamily = FontFamily(Font(R.font.montserrat_regular)))
            }

        }

        
    }



}
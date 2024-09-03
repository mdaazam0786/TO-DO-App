package com.example.to_do.ui.theme.todo_list

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel(),
){
    val todos = viewModel.todos.collectAsState(initial = emptyList())
    val scaffoldState = remember {
        SnackbarHostState()
    }



    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{event->
            when(event){
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(event.message, actionLabel = event.action)
                }
                is UiEvent.Navigate -> onNavigate(event)

                else -> Unit
            }

        }
        
    }
    Box(modifier = Modifier.fillMaxSize()){
        Scaffold(
            floatingActionButton = {
                Button(modifier = Modifier
                    .size(80.dp)
                    .background(color = Color.Transparent, shape = RoundedCornerShape(50)),
                    colors = ButtonDefaults.buttonColors(Color(0xffF76C6A)),
                    onClick = {
                        viewModel.onHomeEvent(HomeScreenEvent.OnAddTodoClick)
                }){
                    Text(text = "+", fontSize = 44.sp, color = Color.White)

                }
            }
        ){
            LazyColumn (modifier = Modifier
                .padding(it)
                .fillMaxSize()){
                items(1) {
                    Image(
                        painter = painterResource(id = R.drawable.to_do),
                        contentDescription = null,
                        alignment = Alignment.TopStart,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.size(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.union),
                            contentDescription = null
                        )
                        Text(
                            text = "LIST OF TODO",
                            color = Color(0xffF76C6A),
                            fontFamily = FontFamily(
                                Font(R.font.bebasneue_regular)
                            ),
                            fontSize = 34.sp
                        )

                    }
                }
                items(todos.value){todo->
                    TodoItem(todo = todo, event = viewModel::onHomeEvent)
                }
            }

        }

    }
}

@Composable
fun TodoItem(
    todo : Todo,
    event : (HomeScreenEvent) -> Unit,
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .height(150.dp)
        .clickable {
            event(
                HomeScreenEvent.OnTodoClick(todo)
            )
        },
        colors = CardDefaults.cardColors(
            containerColor = if (todo.isDone) Color(0xffF79E89) else Color(0xffF76C6A)
        )

    ){
        Column {
            if(!todo.isDone){
                Column{
                    Text(text = "Created At : ${todo.date}",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp)
                    )
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = todo.title,
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Image(
                            painter = painterResource(id = R.drawable.pending),
                            contentDescription = null,
                            alignment = Alignment.TopEnd
                        )
                    }
                    todo.description?.let {
                        Text(
                            text = it,
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(16.dp))
                    }
                }
            }else{
                Column{
                    Text(text = "Created At : ${todo.date}",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = todo.title,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp , top = 16.dp)
                    )
                    todo.description?.let {
                        Text(
                            text = it,
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

            }
        }

    }
}

package com.example.todoapplication.main

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapplication.R
import com.example.todoapplication.model.ToDo

@Composable
fun MainScreen(navController: NavController, model: MainScreenViewModel){

    val todoList = model.todoList.collectAsState(emptyList())
    
    Scaffold(topBar = {MainTopBar(navController)}) {
        ToDoList(todoList){todo -> navController.navigate("edit/${todo.id}")}
    }
}

@Composable
fun ToDoList(list: State<List<ToDo>>, itemSelected: (todo: ToDo) -> Unit) {
    Box(modifier = Modifier
        .background(Color.Blue)
        .fillMaxSize()
    ){
        LazyColumn{
            items(list.value) { todo -> ToDoListItem(todo, itemSelected) }
        }
    }
}

@Composable
fun ToDoListItem(todo: ToDo, itemSelected: (todo: ToDo) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()
        .fillMaxSize()
        .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(10)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clickable { itemSelected(todo) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(todo.title, style = MaterialTheme.typography.subtitle1)
            Text(
                DateFormat.format("yyyy/MM/dd hh:mm:ss", todo.created).toString(),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun MainTopBar(navController: NavController) {
    TopAppBar(title = { Text(stringResource(id = R.string.app_name))},
        actions = {IconButton(onClick = {  navController.navigate("create")}){
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
        }
    )
}



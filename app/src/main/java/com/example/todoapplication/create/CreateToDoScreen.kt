package com.example.todoapplication.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapplication.R

@Composable
fun CreateToDoScreen(navController: NavController, model: CreateViewModel){

    val scaffoldState = rememberScaffoldState()

    val title = rememberSaveable { mutableStateOf("")}
    val contents = rememberSaveable { mutableStateOf("")}
    val errorMessage = model.errorMessage.collectAsState()
    val done = model.done.collectAsState()

    if (errorMessage.value.isNotEmpty()){
        LaunchedEffect(scaffoldState.snackbarHostState){
            scaffoldState.snackbarHostState.showSnackbar(message = errorMessage.value)
            model.errorMessage.value = ""
        }
    }

    if (done.value){
        model.done.value = false
        navController.popBackStack()
    }
    Scaffold(scaffoldState = scaffoldState,
        topBar = {CreateTopBar(navController){model.save(title.value, contents.value)} }
    ) {
        CreateToDoBody(title, contents)
    }
}

@Composable
fun CreateToDoBody(title: MutableState<String>, contents: MutableState<String>) {
    Column {
        OutlinedTextField(value = title.value, onValueChange = { title.value = it },
            label = {Text(stringResource(id = R.string.todo_title))},
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        OutlinedTextField(value = contents.value, onValueChange = { contents.value = it },
            label = { Text(stringResource(id = R.string.todo_detail)) },
            modifier = Modifier
                .weight(1.0f, true)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun CreateTopBar(navController: NavController, save: () -> Unit) {
    TopAppBar(navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Filled.ArrowBack, "Back")
        }
    },
        title = {Text(stringResource(id = R.string.create_todo))},
        actions = {
            IconButton(onClick = save) {
                Icon(Icons.Filled.Edit, "Save")
            }
        }
    )
}

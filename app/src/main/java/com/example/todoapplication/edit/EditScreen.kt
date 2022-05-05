package com.example.todoapplication.edit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapplication.R
import com.example.todoapplication.model.ToDo

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditScreen(navController: NavController, model: EditViewModel){

    val scaffoldState = rememberScaffoldState()
    val todo = model.todo.collectAsState(emptyToDo)
    if (todo.value.id == emptyToDoId) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                EditTopBar(navController, null)
            },
        ) {

            CircularProgressIndicator()
        }
        return
    }
    val title = rememberSaveable { mutableStateOf(todo.value.title) }
    val contents = rememberSaveable { mutableStateOf(todo.value.contents) }

    val errorMessage = model.errorMessage.collectAsState()
    val done = model.done.collectAsState()
    val deleted = model.deleted.collectAsState()

    if (deleted.value) {
        model.deleted.value = false
        navController.popBackStack()
    }

    if (errorMessage.value.isNotEmpty()) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(message = errorMessage.value)
            model.errorMessage.value = ""
        }
    }

    if (done.value) {
        model.done.value = false
        navController.popBackStack()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar ={
            EditTopBar(navController) {
                model.save(todo.value, title.value, contents .value)
            }
        }

    ) {
        EditToDoBody(title, contents){
             model.delete(todo.value)
        }
    }
}

@Composable
fun EditToDoBody(title: MutableState<String>, contents: MutableState<String>, deleted: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    Column {
        OutlinedTextField(
            value =title.value,
            onValueChange = { title.value = it },
            label = { Text(stringResource(id = R.string.todo_title)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        OutlinedTextField(
            value = contents.value,
            onValueChange = {contents.value = it },
            label = { Text(stringResource(id = R.string.todo_detail)) },
            modifier = Modifier
                .weight(1.0f, true)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.End) {
            Button(modifier = Modifier.size(50.dp),
                onClick = { true.also { showDialog = it } },
                shape = CircleShape,
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Blue,
                    contentColor = Color.White,))
            {
                Icon(Icons.Filled.Delete,  contentDescription = "delete")
            }
        }
    }
    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false  },
            title = { Text(stringResource(id = R.string.delete_message))},
            confirmButton = {
                TextButton(onClick = deleted) {
                    Text(stringResource(id = R.string.delete_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(id =R.string.delete_cancel))
                }
            }
        )
    }
}
@Composable
fun EditTopBar(navController: NavController,  save: (() -> Unit)?) {

    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        },
        title = {
            Text(stringResource(id = R.string.edit_todo))
        },
        actions = {
            if (save != null) {
                IconButton(onClick = save) {
                    Icon(painter = painterResource(id = R.drawable.save), "save")
                }
            }
        }
    )
}

private const val emptyToDoId = -1
private val emptyToDo = ToDo(
    id = emptyToDoId,
    title = "",
    contents = "",
    created = 0,
    modified = 0
)

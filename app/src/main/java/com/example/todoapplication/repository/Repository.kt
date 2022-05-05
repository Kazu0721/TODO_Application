package com.example.todoapplication.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.todoapplication.model.RoomSingleton
import com.example.todoapplication.model.ToDo
import com.example.todoapplication.model.ToDoDao
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun  create (title: String, contents: String): ToDo

    fun getAll(): Flow<List<ToDo>>

    fun getById (todoId: Int): Flow<ToDo>

    suspend fun update(todo: ToDo, title: String, contents: String)

    suspend fun delete(todo: ToDo)
}
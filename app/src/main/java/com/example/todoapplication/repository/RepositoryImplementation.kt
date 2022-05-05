package com.example.todoapplication.repository

import com.example.todoapplication.model.ToDo
import com.example.todoapplication.model.ToDoDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(private val dao: ToDoDao): Repository {
    override suspend fun create(title: String, contents: String): ToDo {
        val todo = ToDo(title = title, contents = contents, created = System.currentTimeMillis(), modified = System.currentTimeMillis())
        dao.create(todo)
        return todo
    }

    override fun getAll(): Flow<List<ToDo>> {
        return dao.getAll()
    }

    override fun getById(todoId: Int): Flow<ToDo> {
        return dao.getById(todoId).take(1).map { list -> list[0] }
    }

    override suspend fun update(todo: ToDo, title: String, contents: String) {
        val updateToDo = ToDo(id = todo.id, title = title, contents = contents, created = todo.created, modified = System.currentTimeMillis())
        dao.update(updateToDo)
    }

    override suspend fun delete(todo: ToDo) {
        dao.delete(todo)
    }
}
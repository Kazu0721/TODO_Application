package com.example.todoapplication.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.model.ToDo
import com.example.todoapplication.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel  @Inject constructor(private val repo: Repository) : ViewModel(){

    private val todoId = MutableStateFlow(-1)

    @ExperimentalCoroutinesApi
    val todo: Flow<ToDo> = todoId.flatMapLatest { todoId -> repo.getById(todoId) }

    val errorMessage = MutableStateFlow("")
    val done = MutableStateFlow(false)
    val deleted = MutableStateFlow(false)

    fun setId(todoId: Int) {
        this.todoId.value = todoId
    }

    fun save(todo: ToDo, title: String, contents: String) {
        if (title.trim().isEmpty()) {
            errorMessage.value = "Input title"
            return
        }
        viewModelScope.launch {
            try {
                repo.update(todo, title, contents)
                done.value = true
            } catch (e: Exception) {
                errorMessage.value = e.message.toString()
            }
        }
    }
    fun delete(todo: ToDo) {
        viewModelScope.launch {
            try {
                repo.delete(todo)
                deleted.value = true
            } catch (e: Exception) {
                errorMessage.value = e.message ?: ""
            }
        }
    }
}
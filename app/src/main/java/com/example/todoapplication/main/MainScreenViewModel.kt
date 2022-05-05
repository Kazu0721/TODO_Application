package com.example.todoapplication.main

import androidx.lifecycle.ViewModel
import com.example.todoapplication.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repo: Repository): ViewModel() {
    val todoList = repo.getAll()
}
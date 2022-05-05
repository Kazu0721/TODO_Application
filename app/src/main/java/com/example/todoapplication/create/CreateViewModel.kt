package com.example.todoapplication.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(private val repo: Repository): ViewModel() {

    val errorMessage = MutableStateFlow("")
    val done = MutableStateFlow(false)

    fun save(title: String, contents: String){
        if (title.trim().isEmpty()){
            errorMessage.value = "Input Title"
            return
        }
        viewModelScope.launch {
            try{
                repo.create(title, contents)
                done.value = true
            }catch (e: Exception){
                errorMessage.value = e.message ?: ""
            }
        }
    }
}
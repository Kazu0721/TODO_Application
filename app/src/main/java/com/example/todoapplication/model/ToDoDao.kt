package com.example.todoapplication.model


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("select * from ToDo where created < :startCreated order by created desc limit :limit")
    fun getWithCreated(startCreated: Long, limit: Int): Flow<List<ToDo>>

    @Query("select * from ToDo order by id desc")
    fun getAll(): Flow<List<ToDo>>

    @Query("select * from ToDo where id=:todoId limit 1")
    fun getById(todoId: Int): Flow<List<ToDo>>

    @Insert
    suspend fun create(todo: ToDo)

    @Update
    suspend fun update(todo: ToDo)

    @Delete
    suspend fun delete(todo: ToDo)
}
package com.example.todoapplication.model



import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ToDo::class], version = 1, exportSchema = false)
abstract class RoomSingleton: RoomDatabase() {
    abstract fun todoDao(): ToDoDao

}
package com.example.todoapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var title: String,
    var contents: String,
    val created: Long,
    val modified: Long
        )
package com.lelandcer.twodo.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ToDoTask(
    @PrimaryKey val id: String,
    @ColumnInfo val listId: String,
    @ColumnInfo val name: String,
    @ColumnInfo val createdAt: Int
)


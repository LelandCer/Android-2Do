package com.lelandcer.twodo.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lelandcer.twodo.models.id.Id
import java.util.*
import kotlin.collections.ArrayList
import com.lelandcer.twodo.models.list.ToDoList as ExToDoList

@Entity
class ToDoList(
    @PrimaryKey var id: Id,
    @ColumnInfo var name: String,
    @ColumnInfo var dueAt: Date,
    @ColumnInfo var createdAt: Date,
    @ColumnInfo var updatedAt: Date
) {


    fun toToDoList(): ExToDoList {
        return ExToDoList(id, name, ArrayList(), dueAt, createdAt, updatedAt)
    }

    companion object {
        fun fromToDoList(toDoList: ExToDoList): ToDoList {
            with(toDoList) {
                return ToDoList(
                    id,
                    name,
                    dueAt,
                    createdAt,
                    updatedAt
                )
            }
        }
    }
}
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
    @PrimaryKey var id: String,
    @ColumnInfo var name: String,
    @ColumnInfo var dueAt: Date,
    @ColumnInfo var createdAt: Date,
    @ColumnInfo var updatedAt: Date
) {



    fun toToDoList(): ExToDoList {
        return ExToDoList(StringId(id), name, ArrayList(), dueAt, createdAt, updatedAt)
    }

    class StringId(private val key: String) : Id {
        override fun getKey(): String {
            return key
        }

    }
    companion object {
        fun fromToDoList(toDoList: ExToDoList): ToDoList {
            return ToDoList(
                toDoList.id.getKey(),
                toDoList.name,
                toDoList.dueAt,
                toDoList.createdAt,
                toDoList.updatedAt
            )
        }
    }
}
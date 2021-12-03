package com.lelandcer.twodo.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lelandcer.twodo.models.id.Id
import java.util.*
import com.lelandcer.twodo.models.task.ToDoTask as ExToDoTask

@Entity
class ToDoTask(
    @PrimaryKey val id: Id,
    @ColumnInfo val listId: Id,
    @ColumnInfo val name: String,
    @ColumnInfo val createdAt: Date
) {


    fun toToDoTask(): ExToDoTask {
        return ExToDoTask(
            id, listId, name, createdAt
        )
    }

    companion object {
        fun fromToDoTask(toDoTask: ExToDoTask): ToDoTask {
            with(toDoTask) {
                return ToDoTask(
                    id, listId, name, createdAt
                )
            }

        }
    }
}


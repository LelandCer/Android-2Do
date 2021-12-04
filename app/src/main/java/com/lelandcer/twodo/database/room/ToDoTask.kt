package com.lelandcer.twodo.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lelandcer.twodo.models.id.Id
import java.util.*
import com.lelandcer.twodo.models.task.ToDoTask as ExToDoTask

@Entity
// A class for serializing a ToDoTask into a room storable object
class ToDoTask(
    @PrimaryKey val id: Id,
    @ColumnInfo val listId: Id,
    @ColumnInfo val name: String,
    @ColumnInfo val createdAt: Date,
    @ColumnInfo val isCompleted: Boolean,
    @ColumnInfo val completedAt: Date?,

) {


    fun toToDoTask(): ExToDoTask {
        return ExToDoTask(
            id, listId, name, createdAt, isCompleted, completedAt
        )
    }

    companion object {
        fun fromToDoTask(toDoTask: ExToDoTask): ToDoTask {
            with(toDoTask) {
                return ToDoTask(
                    id, listId, name, createdAt, isCompleted, completedAt
                )
            }

        }
    }
}


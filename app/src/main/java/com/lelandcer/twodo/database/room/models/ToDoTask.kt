package com.lelandcer.twodo.database.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lelandcer.twodo.models.id.Id
import java.util.*
import com.lelandcer.twodo.models.task.ToDoTask as ExToDoTask

@Entity
/** A class for serializing a ToDoTask into a Room storable object */
class ToDoTask(
    @PrimaryKey val id: Id,
    @ColumnInfo val listId: Id,
    @ColumnInfo val name: String,
    @ColumnInfo val createdAt: Date,
    @ColumnInfo val isCompleted: Boolean,
    @ColumnInfo val completedAt: Date?,

    ) {

    /** Deserializes this into a ToDoTask that can be used outside of the package */
    fun toToDoTask(): ExToDoTask {
        return ExToDoTask(
            id, listId, name, createdAt, isCompleted, completedAt
        )
    }

    /** Serializes an external ToDoTask into one that the db can store */
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


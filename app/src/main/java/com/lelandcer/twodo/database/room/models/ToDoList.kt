package com.lelandcer.twodo.database.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lelandcer.twodo.models.id.Id
import java.util.*
import kotlin.collections.ArrayList
import com.lelandcer.twodo.models.list.ToDoList as ExToDoList

@Entity
/** A class for serializing a ToDoList into a Room storable object */
class ToDoList(
    @PrimaryKey val id: Id,
    @ColumnInfo val name: String,
    @ColumnInfo val dueAt: Date,
    @ColumnInfo val createdAt: Date,
    @ColumnInfo val updatedAt: Date
) {

    /** Deserializes this into a ToDoList that can be used outside of the package */
    fun toToDoList(): ExToDoList {
        return ExToDoList(id, name, ArrayList(), dueAt, createdAt, updatedAt)
    }

    /** Serializes an external ToDoList into one that the db can store */
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
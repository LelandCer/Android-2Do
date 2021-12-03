package com.lelandcer.twodo.database

import androidx.room.TypeConverter
import com.lelandcer.twodo.database.models.StringId
import com.lelandcer.twodo.models.id.Id
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromString(value: String): Id {
        return StringId(value)
    }

    @TypeConverter
    fun idToString(id: Id): String {
        return id.getKey()
    }
}
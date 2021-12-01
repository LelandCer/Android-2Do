package com.lelandcer.twodo.features.list

import com.lelandcer.twodo.models.list.ToDoList
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/** A display implementation for ToDoList that is less "business" and more "fun" */
class FriendlyToDoListDisplay @Inject constructor() : ToDoListDisplay {
    private var toDoList: ToDoList? = null

    private val soonDate = 1
    override fun forToDoList(toDoList: ToDoList): ToDoListDisplay {
        this.toDoList = toDoList
        return this
    }

    override fun completionRatio(): String {
        toDoList?.let {
            return " ${it.taskCompletedCount()} / ${it.taskTotalCount()} "
        }
        return ""
    }

    override fun name(): String {
        return toDoList?.name ?: ""
    }

    override fun dueAt(): String {
        toDoList?.let {
            if (isComplete()) return "Done"
            val difference = getDifferenceInDays(it.dueAt)
            // TODO resources
            if (difference < 0) return "Late"
            return when (difference) {
                0 -> "Today"
                in 1..soonDate -> "Soon"
                in (soonDate+1)..9 -> "$difference Days"
                else -> dueAtDateFormat()
            }

        }
        return dueAtDateFormat()
    }

    override fun dueAtDateFormat(): String {
        toDoList?.let {
            return formatDate(it.dueAt)
        }
        return "?.?"
    }

    override fun formatDate(date: Date): String {
        val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        format.timeZone = Calendar.getInstance().timeZone
        return format.format(date)
    }

    override fun isComplete(): Boolean {
         return toDoList!!.isCompleted()
    }

    override fun isSoon(): Boolean {
        val difference = getDifferenceInDays(toDoList!!.dueAt)
        return difference in 0..soonDate
    }

    override fun isLate(): Boolean {
        val difference = getDifferenceInDays(toDoList!!.dueAt)
        return difference < 0
    }

    private fun getDateForToday(): Date {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        return calendar.time
    }

    private fun getDifferenceInDays(date: Date): Int {
        val daysInMilli = 24 * 60 * 60 * 1000
        val difference = date.time - getDateForToday().time
        val days = (kotlin.math.floor((difference / daysInMilli).toDouble())).toInt()
        if (days in -10..10) {
            val calendarToday = Calendar.getInstance()
            val calendarDate = Calendar.getInstance()
            calendarDate.time = date
            return calendarDate[Calendar.DAY_OF_YEAR] - calendarToday[Calendar.DAY_OF_YEAR]
        }
        return days

    }
}
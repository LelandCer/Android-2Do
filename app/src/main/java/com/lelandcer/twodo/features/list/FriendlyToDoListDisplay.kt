package com.lelandcer.twodo.features.list

import com.lelandcer.twodo.models.list.ToDoList
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/** A display implementation for ToDoList that is less "business" and more "fun" */
class FriendlyToDoListDisplay @Inject constructor() : ToDoListDisplay {
    private var toDoList: ToDoList? = null
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
            var difference = getDifferenceInDays(it.dueAt)
            if(difference < 0) return "Failed"
            return when(difference) {
                0 -> "Today"
                1 -> "Tomorrow"
                in 2..9 -> "$difference Days"
                else -> dueAtDateFormat()
            }

        }
        return dueAtDateFormat()
    }

    override fun dueAtDateFormat(): String {
        toDoList?.let {
            return dateToString(it.dueAt)
        }
        return "?.?"
    }

    private fun dateToString(date: Date): String {
        val newText: String
        if (date < getDateForToday()) {
            newText = "Too late now!"
        } else {
            val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("UTC")
            newText = format.format(date)
        }

        return newText
    }

    private fun getDateForToday(): Date? {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        return calendar.time
    }

    private fun getDifferenceInDays(date: Date): Int{
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        val difference = date.time - Date().time
        return (difference / daysInMilli).toInt()

    }
}
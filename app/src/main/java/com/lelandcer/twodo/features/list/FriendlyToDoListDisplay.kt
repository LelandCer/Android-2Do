package com.lelandcer.twodo.features.list

import com.lelandcer.twodo.models.list.ToDoList
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/** A display implementation for ToDoList that is less "business" and more "fun" */
class FriendlyToDoListDisplay @Inject constructor() : ToDoListDisplay {
    private lateinit var toDoList: ToDoList

    override fun forToDoList(toDoList: ToDoList): ToDoListDisplay {
        this.toDoList = toDoList
        return this
    }

    override fun completionRatio(): String {

        return " ${toDoList.toDoTaskCompletedCount()} / ${toDoList.toDoTaskTotalCount()} "

    }

    override fun name(): String {
        return toDoList.name
    }

    override fun dueAt(): String {
        if (isComplete()) return DONE_LABEL
        val difference = getDifferenceInDays(toDoList.dueAt)
        if (difference < 0) return LATE_LABEL
        return when (difference) {
            0 -> TODAY_LABEL
            in 1..SOON_DATE -> SOON_LABEL
            in (SOON_DATE + 1)..9 -> "$difference $COUNTER_LABEL"
            else -> dueAtDateFormat()
        }
    }

    override fun dueAtDateFormat(): String {
        return formatDate(toDoList.dueAt)

    }

    override fun formatDate(date: Date): String {
        val format = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        format.timeZone = Calendar.getInstance().timeZone
        return format.format(date)
    }

    override fun isComplete(): Boolean {
        return toDoList.isCompleted()
    }

    override fun isSoon(): Boolean {
        val difference = getDifferenceInDays(toDoList.dueAt)
        return difference in 0..SOON_DATE
    }

    override fun isLate(): Boolean {
        val difference = getDifferenceInDays(toDoList.dueAt)
        return difference < 0
    }

    /** Get the date for today, without any time components */
    private fun getDateForToday(): Date {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        return calendar.time
    }

    /** Get the difference from now to the provided date in calendar days. */
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

    companion object {
        // TODO convert to translatable resources
        private const val DONE_LABEL: String = "Done"
        private const val LATE_LABEL: String = "Late"
        private const val SOON_LABEL: String = "Soon"
        private const val TODAY_LABEL: String = "Today"
        private const val DATE_FORMAT: String = "yyyy.MM.dd"
        private const val COUNTER_LABEL: String = "Days"

        // Number of days that is considered soon.
        private const val SOON_DATE = 1
    }
}
package com.mitch.androidutils.utils.formatting

import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.Locale

fun Locale.sortedDaysOfWeek(): List<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(this).firstDayOfWeek

    return DayOfWeek.entries
        .partition { it == firstDayOfWeek }
        .let { it.first + it.second }
}

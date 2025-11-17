package com.example.immunify.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatFullDate(date: LocalDate): String {

    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val month = date.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val day = date.dayOfMonth

    val suffix = when {
        day in listOf(11, 12, 13) -> "th"
        day % 10 == 1 -> "st"
        day % 10 == 2 -> "nd"
        day % 10 == 3 -> "rd"
        else -> "th"
    }

    return "$dayOfWeek, ${day}$suffix $month ${date.year}"
}
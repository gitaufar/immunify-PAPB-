package com.example.immunify.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatFullDate(rawDate: String): String {
    val date = LocalDate.parse(rawDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val month = date.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val day = date.dayOfMonth
    val year = date.year

    val suffix = getDaySuffix(day)

    return "$dayOfWeek, $day$suffix $month $year"
}

private fun getDaySuffix(day: Int): String {
    return if (day in 11..13) {
        "th"
    } else {
        when(day % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }
}

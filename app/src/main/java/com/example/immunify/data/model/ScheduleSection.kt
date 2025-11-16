package com.example.immunify.data.model

data class ScheduleSection(
    val title: String,
    val icon: Int,
    val times: List<TimeSlot>
)
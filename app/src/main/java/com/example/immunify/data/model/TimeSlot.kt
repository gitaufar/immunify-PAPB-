package com.example.immunify.data.model

data class TimeSlot(
    val time: String,
    var isBooked: Boolean = false,
    var isSelected: Boolean = false
)
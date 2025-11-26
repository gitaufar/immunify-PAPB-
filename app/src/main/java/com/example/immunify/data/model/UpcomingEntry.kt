package com.example.immunify.data.model

import java.time.LocalDate

data class UpcomingEntry(
    val vaccine: VaccineData,
    val date: LocalDate
)

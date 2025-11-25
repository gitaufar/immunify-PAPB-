package com.example.immunify.data.model

import java.time.LocalDate

data class CompletedEntry(
    val vaccine: VaccineData,
    val date: LocalDate
)

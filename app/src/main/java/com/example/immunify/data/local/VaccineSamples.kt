package com.example.immunify.data.local

import com.example.immunify.data.model.VaccineData

val VaccineSamples = listOf(
    VaccineData(
        id = "v1",
        name = "HPV",
        scheduledDates = listOf("2025-11-10")
    ),
    VaccineData(
        id = "v2",
        name = "Influenza",
        scheduledDates = listOf("2025-11-12")
    ),
    VaccineData(
        id = "v3",
        name = "Varicella (Chicken Pox)",
        scheduledDates = listOf("2026-05-07")
    )
)
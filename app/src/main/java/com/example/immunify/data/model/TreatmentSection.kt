package com.example.immunify.data.model

data class TreatmentSection(
    val overview: String,                   // contoh: "There is no cure for polio..."
    val categories: List<TreatmentCategory> // contoh: Lifestyle, Therapy, Medications
)
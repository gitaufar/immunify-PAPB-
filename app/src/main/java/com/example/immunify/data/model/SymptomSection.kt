package com.example.immunify.data.model

data class SymptomSection(
    val summaryTitle: String,               // contoh: "Requires a medical diagnosis"
    val summaryDescription: String,         // contoh: deskripsi ringkas di bawahnya
    val categories: List<SymptomCategory>   // contoh: Whole body, Muscular, Also common
)
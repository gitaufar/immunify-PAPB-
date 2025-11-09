package com.example.immunify.data.model

data class DiseaseData(
    val id: String,
    val name: String,
    val imageRes: Int,
    val keyFacts: List<String>,
    val overview: String,
    val symptoms: List<SymptomSection>,
    val treatments: List<TreatmentSection>
)
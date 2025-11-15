package com.example.immunify.data.local

import com.example.immunify.data.model.ChildData

val ChildSamples = listOf(
    ChildData(
        id = "child_001",
        name = "Jane Doe",
        birthDate = "2020-05-22",
        gender = "Female",
        vaccinationHistory = listOf(
            VaccinationSample1
        )
    ),
    ChildData(
        id = "child_002",
        name = "John Doe",
        birthDate = "2022-09-10",
        gender = "Male",
        vaccinationHistory = listOf(
            VaccinationSample2
        )
    )
)
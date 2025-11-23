package com.example.immunify.data.local

import com.example.immunify.data.model.ChildData
import com.example.immunify.data.model.Gender

val ChildSamples = listOf(
    ChildData(
        id = "child_001",
        name = "Jane Doe",
        birthDate = "2020-05-22",
        gender = Gender.FEMALE,
        vaccinationHistory = listOf(
            VaccinationSample1
        )
    ),
    ChildData(
        id = "child_002",
        name = "John Doe",
        birthDate = "2022-09-10",
        gender = Gender.MALE,
        vaccinationHistory = listOf(
            VaccinationSample2
        )
    ),
    ChildData(
        id = "child_003",
        name = "Jake Doe",
        birthDate = "2024-03-30",
        gender = Gender.MALE,
        vaccinationHistory = listOf(
            VaccinationSample3
        )
    )
)
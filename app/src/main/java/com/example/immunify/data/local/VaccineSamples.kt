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

val ClinicVaccineSamples = listOf(
    VaccineData(
        id = "v1",
        name = "HPV vaccine",
        description = listOf(
            "HPV vaccine protects against the sexually transmitted human papillomavirus.",
            "Recommended for both genders, typically at ages 11–12 or as early as 9.",
            "Administered in a series of 2–3 doses.",
            "Highly effective in preventing certain cancers and genital warts.",
            "Generally safe with mild side effects such as pain, redness, or swelling at the injection site."
        ),
        brand = listOf("Gardasil", "Cervarix")
    ),
    VaccineData(
        id = "v2",
        name = "Hepatitis B vaccine",
        description = listOf(
            "Protects against hepatitis B virus infection.",
            "Recommended for all infants and unvaccinated adults at risk.",
            "Typically given as a series of 3–4 doses over 6 months."
        ),
        brand = listOf("Engerix-B", "Recombivax HB")
    ),
    VaccineData(
        id = "v3",
        name = "Varicella vaccine",
        description = listOf(
            "Protects against chickenpox caused by the varicella-zoster virus.",
            "Usually given in 2 doses, starting from 12–15 months of age.",
            "Helps prevent both mild and severe chickenpox infections."
        ),
        brand = listOf("Varivax", "ProQuad")
    )
)
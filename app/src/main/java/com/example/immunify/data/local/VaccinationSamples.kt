package com.example.immunify.data.local

import com.example.immunify.data.model.VaccinationData

val VaccinationSample1 = VaccinationData(
    id = "vac_001",
    vaccinant = "Jane Doe",
    immunizationType = "Primary",
    lotNumber = "LOT-12345",
    dose = "1st Dose",
    administrator = "EMC Pulomas Clinic",
    vaccine = VaccineSample1
)

val VaccinationSample2 = VaccinationData(
    id = "vac_002",
    vaccinant = "John Doe",
    immunizationType = "Booster",
    lotNumber = "LOT-98765",
    dose = "2nd Dose",
    administrator = "RS Hermina",
    vaccine = VaccineSample2
)

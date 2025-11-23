package com.example.immunify.data.local

import com.example.immunify.data.model.VaccinationData

val VaccinationSample1 = VaccinationData(
    id = "vac_1",
    vaccinant = "Jane Doe",
    immunizationType = "Primary",
    lotNumber = "LOT-12345",
    dose = "1st Dose",
    administrator = "Sarah Smith",
    vaccine = VaccineRecordSamples[0]
)

val VaccinationSample2 = VaccinationData(
    id = "vac_2",
    vaccinant = "John Doe",
    immunizationType = "Booster",
    lotNumber = "LOT-98765",
    dose = "2nd Dose",
    administrator = "Olivia Johnson",
    vaccine = VaccineRecordSamples[1]
)

val VaccinationSample3 = VaccinationData(
    id = "vac_3",
    vaccinant = "Jake Doe",
    immunizationType = "Mandatory",
    lotNumber = "LOT-34567",
    dose = "1st Dose",
    administrator = "Marry Smith",
    vaccine = VaccineRecordSamples[2]
)
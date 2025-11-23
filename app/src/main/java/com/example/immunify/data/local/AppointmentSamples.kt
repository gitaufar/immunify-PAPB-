package com.example.immunify.data.local

import com.example.immunify.data.model.*

val AppointmentSamples: List<AppointmentData> by lazy {
    val parent = UserSample
    val clinic = ClinicSamples.first()
    val children = ChildSamples

    val timeSlots = listOf(
        "08:00 - 09:30",
        "10:00 - 11:30",
        "13:00 - 14:30",
        "15:00 - 16:30"
    )

    VaccineSamples.mapIndexed { index, vaccine ->
        val scheduledDate = vaccine.scheduledDates.firstOrNull() ?: "2025-12-01"
        val timeSlot = timeSlots[index % timeSlots.size]

        val vaccinants = when (index) {
            0 -> listOf(children[0])
            1 -> listOf(children[1])
            else -> listOf(children[0], children[2])
        }

        AppointmentData(
            id = "apt_${index + 1}",
            parent = parent,
            clinic = clinic,
            date = scheduledDate,
            timeSlot = timeSlot,
            vaccine = vaccine,
            vaccinants = vaccinants,
            status = AppointmentStatus.PENDING
        )
    }
}

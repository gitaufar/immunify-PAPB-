package com.example.immunify.data.model

data class AppointmentData(
    val id: String,
    val parent: UserData,
    val clinic: ClinicData,
    val date: String,
    val timeSlot: String,
    val vaccine: VaccineData,
    val vaccinants: List<ChildData>,
    val status: AppointmentStatus = AppointmentStatus.PENDING
)

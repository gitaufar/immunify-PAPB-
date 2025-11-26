package com.example.immunify.data.firebase.dto

import com.example.immunify.data.model.ChildData
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserDto(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var phoneNumber: String? = null,
    var children: List<ChildData>? = emptyList()
) {
    constructor() : this("", "", "", "", "", emptyList())
}
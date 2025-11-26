package com.example.immunify.util

import androidx.compose.ui.graphics.Color
import com.example.immunify.data.model.ChildData
import com.example.immunify.data.model.Gender
import com.example.immunify.ui.theme.PrimaryMain
import com.example.immunify.ui.theme.getChildColor

fun getAvatarColorForChild(child: ChildData, children: List<ChildData>): Color {
    var maleIndex = 0
    var femaleIndex = 0

    children.forEach { c ->
        if (c.id == child.id) {
            return if (c.gender == Gender.MALE) {
                getChildColor(Gender.MALE, maleIndex)
            } else {
                getChildColor(Gender.FEMALE, femaleIndex)
            }
        }

        if (c.gender == Gender.MALE) maleIndex++
        else femaleIndex++
    }

    return PrimaryMain
}

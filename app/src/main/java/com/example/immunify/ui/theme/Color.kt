package com.example.immunify.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.immunify.data.model.Gender

val PrimaryMain = Color(0xFF008B8B)
val PrimarySurface = Color(0xFFEEFBF8)
val PrimaryBorder = Color(0xFFB1D8D8)
val PrimaryHover = Color(0xFF2A9E9E)
val PrimaryPressed = Color(0xFF004545)

val WarningMain = Color(0xFFCD7B2E)
val WarningSurface = Color(0xFFFFF9F2)
val WarningBorder = Color(0xFFEECEB0)

val Tosca1 = Color(0xFF478FAD)
val Tosca2 = Color(0xFF2BB673)
val Tosca3 = Color(0xFF3C72A1)

val Pink1 = Color(0xFFFFC3BD)
val Pink2 = Color(0xFFD890FF)
val Pink3 = Color(0xFFFF9E80)

val Red = Color(0xFFBD251C)
val Yellow = Color(0xFFFBBC05)

val White10 = Color(0xFFFFFFFF)
val White20 = Color(0xFFFAFAFA)
val Grey30 = Color(0xFFEDEDED)
val Grey40 = Color(0xFFC6C6C6)
val Grey50 = Color(0xFFC2C2C2)
val Grey60 = Color(0xFF9E9E9E)
val Grey70 = Color(0xFF757575)
val Grey80 = Color(0xFF676767)
val Grey90 = Color(0xFF404040)
val Black100 = Color(0xFF0A0A0A)

// pilih warna untuk child berdasarkan gender dan urutan
fun getChildColor(gender: Gender, index: Int): Color {
    val maleColors = listOf(Tosca1, Tosca2, Tosca3)
    val femaleColors = listOf(Pink1, Pink2, Pink3)

    return when (gender) {
        Gender.MALE -> maleColors[index % maleColors.size]
        Gender.FEMALE -> femaleColors[index % femaleColors.size]
    }
}
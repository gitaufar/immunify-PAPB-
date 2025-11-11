package com.example.immunify.ui.navigation

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING1 = "onboarding1"
    const val ONBOARDING2 = "onboarding2"
    const val ONBOARDING3 = "onboarding3"

    const val REGISTER = "register"
    const val LOGIN = "login"

    const val HOME = "home"
    const val CLINICS = "clinics"
    const val CLINIC_MAP = "clinic_map"
    const val CLINIC_DETAIL = "clinic_detail/{clinicId}"
    const val SET_APPOINTMENT = "set_appointment"
    const val APPOINTMENT_SUMMARY = "appointment_summary"
    const val BOOKING_SUCCESS = "booking_success"

    const val TRACKER = "tracker"

    const val INSIGHTS = "insights"
    const val INSIGHT_DETAIL = "insight_detail/{insightId}"

    const val PROFILE = "profile"
    const val NOTIFICATION = "notification"

    const val MAIN_GRAPH = "main_graph"

    fun clinicDetailRoute(clinicId: String): String = "clinic_detail/$clinicId"
}

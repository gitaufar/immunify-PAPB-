package com.example.immunify.ui.navigation

object Routes {

    const val SPLASH = "splash"
    const val ONBOARDING1 = "onboarding1"
    const val ONBOARDING2 = "onboarding2"
    const val ONBOARDING3 = "onboarding3"

    const val REGISTER = "register"
    const val LOGIN = "login"

    const val MAIN_GRAPH = "main_graph"

    const val HOME = "main_home"
    const val CLINICS = "main_clinics"
    const val TRACKER = "main_tracker"
    const val PROFILE = "main_profile"

    const val CLINIC_MAP = "main_clinic_map"
    const val CLINIC_DETAIL = "main_clinic_detail/{clinicId}"
    const val SET_APPOINTMENT = "main_set_appointment/{clinicId}"
    const val APPOINTMENT_SUMMARY = "main_appointment_summary"
    const val APPOINTMENT_SUCCESS = "main_appointment_success"

    const val INSIGHTS = "main_insights"
    const val INSIGHT_DETAIL = "main_insight_detail/{insightId}"

    const val NOTIFICATION = "main_notification"

    fun clinicDetailRoute(clinicId: String): String = "main_clinic_detail/$clinicId"
    fun setAppointmentRoute(clinicId: String): String = "main_set_appointment/$clinicId"
}

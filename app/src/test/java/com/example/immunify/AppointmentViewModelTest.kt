package com.example.immunify

import android.os.Build
import com.example.immunify.data.model.AppointmentData
import com.example.immunify.data.model.AppointmentStatus
import com.example.immunify.data.model.ChildData
import com.example.immunify.data.model.ClinicData
import com.example.immunify.data.model.Gender
import com.example.immunify.data.model.UserData
import com.example.immunify.data.model.VaccineData
import com.example.immunify.domain.model.Appointment
import com.example.immunify.domain.usecase.CancelAppointmentUseCase
import com.example.immunify.domain.usecase.CompleteAppointmentUseCase
import com.example.immunify.domain.usecase.CreateAppointmentUseCase
import com.example.immunify.domain.usecase.GetUserAppointmentsUseCase
import com.example.immunify.ui.clinics.viewmodel.AppointmentUiState
import com.example.immunify.ui.clinics.viewmodel.AppointmentViewModel
import com.example.immunify.util.Result
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalCoroutinesApi::class)
class AppointmentViewModelTest {

    private lateinit var createAppointmentUseCase: CreateAppointmentUseCase
    private lateinit var getUserAppointmentsUseCase: GetUserAppointmentsUseCase
    private lateinit var cancelAppointmentUseCase: CancelAppointmentUseCase
    private lateinit var completeAppointmentUseCase: CompleteAppointmentUseCase
    private lateinit var viewModel: AppointmentViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        createAppointmentUseCase = mockk(relaxed = true)
        getUserAppointmentsUseCase = mockk(relaxed = true)
        cancelAppointmentUseCase = mockk(relaxed = true)
        completeAppointmentUseCase = mockk(relaxed = true)
        
        viewModel = AppointmentViewModel(
            createAppointmentUseCase,
            getUserAppointmentsUseCase,
            cancelAppointmentUseCase,
            completeAppointmentUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Helper function to create test appointment
    private fun createTestAppointment(
        id: String = "apt1",
        userId: String = "user1",
        status: AppointmentStatus = AppointmentStatus.PENDING,
        date: String = "2025-12-15"
    ) = Appointment(
        id = id,
        userId = userId,
        userName = "Test User",
        userPhone = "08123456789",
        clinicId = "clinic1",
        clinicName = "Test Clinic",
        clinicAddress = "Test Address",
        date = date,
        timeSlot = "09:00",
        vaccineId = "vaccine1",
        vaccineName = "BCG",
        vaccinantIds = listOf("child1"),
        vaccinantNames = listOf("Anak Test"),
        status = status
    )

    // Helper function to create test AppointmentData (UI model)
    private fun createTestAppointmentData(
        date: String = "2025-12-15",
        timeSlot: String = "09:00"
    ): AppointmentData {
        val parent = UserData(
            id = "user1",
            name = "Test User",
            email = "test@test.com",
            password = "123456",
            phoneNumber = "08123456789",
            children = emptyList()
        )
        val clinic = ClinicData(
            id = "clinic1",
            name = "Test Clinic",
            imageUrl = "https://example.com/image.jpg",
            address = "Test Address",
            district = "Test District",
            city = "Test City",
            contact = "08123456789",
            latitude = -6.2088,
            longitude = 106.8456,
            rating = 4.5,
            website = "https://example.com",
            openingHours = "08:00 - 17:00",
            availableVaccines = emptyList()
        )
        val vaccine = VaccineData(
            id = "vaccine1",
            name = "BCG",
            description = listOf("BCG Vaccine"),
            brand = listOf("Brand A"),
            scheduledDates = emptyList(),
            completedDates = emptyList(),
            remainingDoses = 1
        )
        val vaccinants = listOf(
            ChildData(
                id = "child1",
                name = "Anak Test",
                birthDate = "2020-01-01",
                gender = Gender.MALE,
                vaccinationHistory = emptyList()
            )
        )
        return AppointmentData(
            id = "apt1",
            parent = parent,
            clinic = clinic,
            date = date,
            timeSlot = timeSlot,
            vaccine = vaccine,
            vaccinants = vaccinants,
            status = AppointmentStatus.PENDING
        )
    }

    // ------------------------- INITIAL STATE -------------------------

    @Test
    fun `initial state is Idle`() {
        assertEquals(AppointmentUiState.Idle, viewModel.createAppointmentState.value)
        assertEquals(AppointmentUiState.Idle, viewModel.userAppointmentsState.value)
    }

    @Test
    fun `initial month is current month`() {
        assertEquals(YearMonth.now(), viewModel.currentMonth.value)
    }

    // ------------------------- CREATE APPOINTMENT SUCCESS -------------------------

    @Test
    fun `createAppointment berhasil - state menjadi Success`() = runTest {
        val appointmentData = createTestAppointmentData()

        coEvery { createAppointmentUseCase(any()) } returns Result.Success("apt1")

        viewModel.createAppointment(appointmentData)
        advanceUntilIdle()

        val state = viewModel.createAppointmentState.value
        assertTrue(state is AppointmentUiState.Success)
        assertTrue((state as AppointmentUiState.Success).message.contains("apt1"))
    }

    // ------------------------- CREATE APPOINTMENT FAILED -------------------------

    @Test
    fun `createAppointment gagal - state menjadi Error`() = runTest {
        val appointmentData = createTestAppointmentData()
        val errorMsg = "Clinic is fully booked"

        coEvery { createAppointmentUseCase(any()) } returns Result.Error(Exception(errorMsg))

        viewModel.createAppointment(appointmentData)
        advanceUntilIdle()

        val state = viewModel.createAppointmentState.value
        assertTrue(state is AppointmentUiState.Error)
        assertEquals(errorMsg, (state as AppointmentUiState.Error).message)
    }

    // ------------------------- GET USER APPOINTMENTS SUCCESS -------------------------

    @Test
    fun `getUserAppointments berhasil - state menjadi AppointmentsLoaded`() = runTest {
        val appointments = listOf(
            createTestAppointment(id = "apt1", date = "2025-12-10"),
            createTestAppointment(id = "apt2", date = "2025-12-20")
        )

        coEvery { getUserAppointmentsUseCase("user1") } returns Result.Success(appointments)

        viewModel.getUserAppointments("user1")
        advanceUntilIdle()

        val state = viewModel.userAppointmentsState.value
        assertTrue(state is AppointmentUiState.AppointmentsLoaded)
        assertEquals(2, (state as AppointmentUiState.AppointmentsLoaded).appointments.size)
    }

    @Test
    fun `getUserAppointments dengan empty list - state tetap AppointmentsLoaded`() = runTest {
        coEvery { getUserAppointmentsUseCase("user1") } returns Result.Success(emptyList())

        viewModel.getUserAppointments("user1")
        advanceUntilIdle()

        val state = viewModel.userAppointmentsState.value
        assertTrue(state is AppointmentUiState.AppointmentsLoaded)
        assertTrue((state as AppointmentUiState.AppointmentsLoaded).appointments.isEmpty())
    }

    // ------------------------- GET USER APPOINTMENTS FAILED -------------------------

    @Test
    fun `getUserAppointments gagal - state menjadi Error`() = runTest {
        val errorMsg = "Network error"

        coEvery { getUserAppointmentsUseCase("user1") } returns Result.Error(Exception(errorMsg))

        viewModel.getUserAppointments("user1")
        advanceUntilIdle()

        val state = viewModel.userAppointmentsState.value
        assertTrue(state is AppointmentUiState.Error)
        assertEquals(errorMsg, (state as AppointmentUiState.Error).message)
    }

    // ------------------------- CANCEL APPOINTMENT SUCCESS -------------------------

    @Test
    fun `cancelAppointment berhasil - state menjadi Success dan refresh appointments`() = runTest {
        coEvery { cancelAppointmentUseCase("user1", "apt1") } returns Result.Success(Unit)
        coEvery { getUserAppointmentsUseCase("user1") } returns Result.Success(emptyList())

        viewModel.cancelAppointment("user1", "apt1")
        advanceUntilIdle()

        val state = viewModel.createAppointmentState.value
        assertTrue(state is AppointmentUiState.Success)
        assertEquals("Appointment cancelled successfully", (state as AppointmentUiState.Success).message)

        // Verify refresh dipanggil
        coVerify { getUserAppointmentsUseCase("user1") }
    }

    // ------------------------- CANCEL APPOINTMENT FAILED -------------------------

    @Test
    fun `cancelAppointment gagal - state menjadi Error`() = runTest {
        val errorMsg = "Cannot cancel past appointment"

        coEvery { cancelAppointmentUseCase("user1", "apt1") } returns Result.Error(Exception(errorMsg))

        viewModel.cancelAppointment("user1", "apt1")
        advanceUntilIdle()

        val state = viewModel.createAppointmentState.value
        assertTrue(state is AppointmentUiState.Error)
        assertEquals(errorMsg, (state as AppointmentUiState.Error).message)
    }

    // ------------------------- COMPLETE APPOINTMENT SUCCESS -------------------------

    @Test
    fun `completeAppointment berhasil - state menjadi Success`() = runTest {
        coEvery { 
            completeAppointmentUseCase(
                userId = "user1",
                appointmentId = "apt1",
                lotNumber = "LOT123",
                dose = "0.5ml",
                administrator = "Dr. Test"
            ) 
        } returns Result.Success(Unit)
        coEvery { getUserAppointmentsUseCase("user1") } returns Result.Success(emptyList())

        viewModel.completeAppointment(
            userId = "user1",
            appointmentId = "apt1",
            lotNumber = "LOT123",
            dose = "0.5ml",
            administrator = "Dr. Test"
        )
        advanceUntilIdle()

        val state = viewModel.createAppointmentState.value
        assertTrue(state is AppointmentUiState.Success)
        assertEquals("Appointment completed successfully", (state as AppointmentUiState.Success).message)
    }

    @Test
    fun `completeAppointment berhasil - auto refresh appointments`() = runTest {
        coEvery { 
            completeAppointmentUseCase(any(), any(), any(), any(), any()) 
        } returns Result.Success(Unit)
        coEvery { getUserAppointmentsUseCase("user1") } returns Result.Success(emptyList())

        viewModel.completeAppointment("user1", "apt1", "LOT123", "0.5ml", "Dr. Test")
        advanceUntilIdle()

        coVerify { getUserAppointmentsUseCase("user1") }
    }

    // ------------------------- COMPLETE APPOINTMENT FAILED -------------------------

    @Test
    fun `completeAppointment gagal - state menjadi Error`() = runTest {
        val errorMsg = "Appointment already completed"

        coEvery { 
            completeAppointmentUseCase(any(), any(), any(), any(), any()) 
        } returns Result.Error(Exception(errorMsg))

        viewModel.completeAppointment("user1", "apt1", "LOT123", "0.5ml", "Dr. Test")
        advanceUntilIdle()

        val state = viewModel.createAppointmentState.value
        assertTrue(state is AppointmentUiState.Error)
        assertEquals(errorMsg, (state as AppointmentUiState.Error).message)
    }

    // ------------------------- CALENDAR NAVIGATION -------------------------

    @Test
    fun `nextMonth - current month bertambah 1`() {
        val initialMonth = viewModel.currentMonth.value

        viewModel.nextMonth()

        assertEquals(initialMonth.plusMonths(1), viewModel.currentMonth.value)
    }

    @Test
    fun `prevMonth - current month berkurang 1`() {
        val initialMonth = viewModel.currentMonth.value

        viewModel.prevMonth()

        assertEquals(initialMonth.minusMonths(1), viewModel.currentMonth.value)
    }

    @Test
    fun `setMonth - current month berubah sesuai parameter`() {
        viewModel.setMonth(6, 2026)

        assertEquals(YearMonth.of(2026, 6), viewModel.currentMonth.value)
    }

    // ------------------------- FILTER APPOINTMENTS BY DATE -------------------------

    @Test
    fun `appointmentsOnDate - filter appointments berdasarkan tanggal`() {
        val targetDate = LocalDate.of(2025, 12, 15)
        val appointments = listOf(
            createTestAppointment(id = "apt1", date = "2025-12-15"),
            createTestAppointment(id = "apt2", date = "2025-12-20"),
            createTestAppointment(id = "apt3", date = "2025-12-15")
        )

        val filtered = viewModel.appointmentsOnDate(appointments, targetDate)

        assertEquals(2, filtered.size)
        assertTrue(filtered.all { LocalDate.parse(it.date) == targetDate })
    }

    @Test
    fun `appointmentsOnDate - tidak ada appointment pada tanggal tertentu`() {
        val targetDate = LocalDate.of(2025, 12, 25)
        val appointments = listOf(
            createTestAppointment(id = "apt1", date = "2025-12-15"),
            createTestAppointment(id = "apt2", date = "2025-12-20")
        )

        val filtered = viewModel.appointmentsOnDate(appointments, targetDate)

        assertTrue(filtered.isEmpty())
    }

    // ------------------------- RESET STATE -------------------------

    @Test
    fun `resetCreateState - state kembali ke Idle`() = runTest {
        coEvery { createAppointmentUseCase(any()) } returns Result.Success("apt1")

        val appointmentData = createTestAppointmentData()

        viewModel.createAppointment(appointmentData)
        advanceUntilIdle()

        // Pastikan state sudah berubah
        assertTrue(viewModel.createAppointmentState.value is AppointmentUiState.Success)

        // Reset
        viewModel.resetCreateState()

        assertEquals(AppointmentUiState.Idle, viewModel.createAppointmentState.value)
    }

    @Test
    fun `resetUserAppointmentsState - state kembali ke Idle`() = runTest {
        coEvery { getUserAppointmentsUseCase("user1") } returns Result.Success(emptyList())

        viewModel.getUserAppointments("user1")
        advanceUntilIdle()

        // Pastikan state sudah berubah
        assertTrue(viewModel.userAppointmentsState.value is AppointmentUiState.AppointmentsLoaded)

        // Reset
        viewModel.resetUserAppointmentsState()

        assertEquals(AppointmentUiState.Idle, viewModel.userAppointmentsState.value)
    }
}

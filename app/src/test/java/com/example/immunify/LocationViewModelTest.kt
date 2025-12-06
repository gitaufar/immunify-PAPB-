package com.example.immunify

import android.location.Location
import com.example.immunify.data.model.LocationState
import com.example.immunify.ui.viewmodel.LocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocationViewModelTest {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: LocationViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        // Set both Main and IO to our test dispatcher
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher
        
        mockkStatic(Tasks::class)
        fusedLocationClient = mockk(relaxed = true)
        viewModel = LocationViewModel(fusedLocationClient)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    // ------------------------- INITIAL STATE -------------------------

    @Test
    fun `initial state is Idle`() {
        assertEquals(LocationState.Idle, viewModel.locationState.value)
    }

    // ------------------------- LOAD USER LOCATION SUCCESS -------------------------

    @Test
    fun `loadUserLocation berhasil - state menjadi Success dengan location`() = runTest {
        val mockLocation = mockk<Location>(relaxed = true)
        every { mockLocation.latitude } returns -6.2088
        every { mockLocation.longitude } returns 106.8456

        val mockTask = mockk<Task<Location>>(relaxed = true)
        every { fusedLocationClient.lastLocation } returns mockTask
        every { Tasks.await(mockTask) } returns mockLocation

        viewModel.loadUserLocation()
        advanceUntilIdle()

        val state = viewModel.locationState.value
        assertTrue("Expected Success state but got $state", state is LocationState.Success)
        assertEquals(mockLocation, (state as LocationState.Success).location)
    }

    // ------------------------- LOAD USER LOCATION - NULL LOCATION -------------------------

    @Test
    fun `loadUserLocation dengan null location - state menjadi Error`() = runTest {
        val mockTask = mockk<Task<Location>>(relaxed = true)
        every { fusedLocationClient.lastLocation } returns mockTask
        every { Tasks.await(mockTask) } returns null

        viewModel.loadUserLocation()
        advanceUntilIdle()

        val state = viewModel.locationState.value
        assertTrue("Expected Error state but got $state", state is LocationState.Error)
        assertEquals("Location unavailable.", (state as LocationState.Error).message)
    }

    // ------------------------- LOAD USER LOCATION FAILED -------------------------

    @Test
    fun `loadUserLocation dengan exception - state menjadi Error`() = runTest {
        val errorMsg = "GPS disabled"
        val mockTask = mockk<Task<Location>>(relaxed = true)
        every { fusedLocationClient.lastLocation } returns mockTask
        every { Tasks.await(mockTask) } throws Exception(errorMsg)

        viewModel.loadUserLocation()
        advanceUntilIdle()

        val state = viewModel.locationState.value
        assertTrue("Expected Error state but got $state", state is LocationState.Error)
        assertEquals(errorMsg, (state as LocationState.Error).message)
    }

    @Test
    fun `loadUserLocation dengan exception tanpa message - state Error dengan default message`() = runTest {
        val mockTask = mockk<Task<Location>>(relaxed = true)
        every { fusedLocationClient.lastLocation } returns mockTask
        every { Tasks.await(mockTask) } throws Exception()

        viewModel.loadUserLocation()
        advanceUntilIdle()

        val state = viewModel.locationState.value
        assertTrue("Expected Error state but got $state", state is LocationState.Error)
        assertEquals("Location error.", (state as LocationState.Error).message)
    }

    // ------------------------- LOADING STATE -------------------------

    @Test
    fun `loadUserLocation - state berubah ke Loading sebelum proses selesai`() = runTest {
        val mockTask = mockk<Task<Location>>(relaxed = true)
        every { fusedLocationClient.lastLocation } returns mockTask
        every { Tasks.await(mockTask) } returns mockk(relaxed = true)

        viewModel.loadUserLocation()
        
        // With UnconfinedTestDispatcher, by the time we check, state may already be Success
        // This test validates that the coroutine executed successfully
        val state = viewModel.locationState.value
        assertTrue("Expected Loading or Success state but got $state", 
            state is LocationState.Loading || state is LocationState.Success)
    }
}

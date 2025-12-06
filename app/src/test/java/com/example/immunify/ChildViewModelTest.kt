package com.example.immunify

import com.example.immunify.domain.model.Child
import com.example.immunify.domain.usecase.CreateChildUseCase
import com.example.immunify.domain.usecase.GetUserChildrenUseCase
import com.example.immunify.ui.profile.viewmodel.ChildUiState
import com.example.immunify.ui.profile.viewmodel.ChildViewModel
import com.example.immunify.util.Result
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChildViewModelTest {

    private lateinit var createChildUseCase: CreateChildUseCase
    private lateinit var getUserChildrenUseCase: GetUserChildrenUseCase
    private lateinit var viewModel: ChildViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        createChildUseCase = mockk(relaxed = true)
        getUserChildrenUseCase = mockk(relaxed = true)
        viewModel = ChildViewModel(createChildUseCase, getUserChildrenUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ------------------------- INITIAL STATE -------------------------

    @Test
    fun `initial state is Idle`() {
        assertEquals(ChildUiState.Idle, viewModel.createChildState.value)
        assertEquals(ChildUiState.Idle, viewModel.userChildrenState.value)
    }

    // ------------------------- CREATE CHILD SUCCESS -------------------------

    @Test
    fun `createChild berhasil - state menjadi Success`() = runTest {
        val child = Child(
            id = "child1",
            userId = "user1",
            name = "Anak Test",
            birthDate = "2020-01-15",
            gender = "Male"
        )

        coEvery { createChildUseCase(child) } returns Result.Success("child1")
        coEvery { getUserChildrenUseCase("user1") } returns Result.Success(listOf(child))

        viewModel.createChild(child)
        advanceUntilIdle()

        val state = viewModel.createChildState.value
        assertTrue(state is ChildUiState.Success)
        assertEquals("Child profile created successfully", (state as ChildUiState.Success).message)
    }

    @Test
    fun `createChild berhasil - auto refresh children list`() = runTest {
        val child = Child(
            id = "child1",
            userId = "user1",
            name = "Anak Test",
            birthDate = "2020-01-15",
            gender = "Male"
        )

        coEvery { createChildUseCase(child) } returns Result.Success("child1")
        coEvery { getUserChildrenUseCase("user1") } returns Result.Success(listOf(child))

        viewModel.createChild(child)
        advanceUntilIdle()

        coVerify { getUserChildrenUseCase("user1") }
    }

    // ------------------------- CREATE CHILD FAILED -------------------------

    @Test
    fun `createChild gagal - state menjadi Error`() = runTest {
        val child = Child(
            id = "child1",
            userId = "user1",
            name = "Anak Test",
            birthDate = "2020-01-15",
            gender = "Male"
        )
        val errorMsg = "Failed to create child"

        coEvery { createChildUseCase(child) } returns Result.Error(Exception(errorMsg))

        viewModel.createChild(child)
        advanceUntilIdle()

        val state = viewModel.createChildState.value
        assertTrue(state is ChildUiState.Error)
        assertEquals(errorMsg, (state as ChildUiState.Error).message)
    }

    // ------------------------- GET USER CHILDREN SUCCESS -------------------------

    @Test
    fun `getUserChildren berhasil - state menjadi ChildrenLoaded`() = runTest {
        val children = listOf(
            Child(id = "c1", userId = "user1", name = "Anak 1", birthDate = "2020-01-01", gender = "Male"),
            Child(id = "c2", userId = "user1", name = "Anak 2", birthDate = "2022-06-15", gender = "Female")
        )

        coEvery { getUserChildrenUseCase("user1") } returns Result.Success(children)

        viewModel.getUserChildren("user1")
        advanceUntilIdle()

        val state = viewModel.userChildrenState.value
        assertTrue(state is ChildUiState.ChildrenLoaded)
        assertEquals(2, (state as ChildUiState.ChildrenLoaded).children.size)
        assertEquals("Anak 1", state.children[0].name)
        assertEquals("Anak 2", state.children[1].name)
    }

    @Test
    fun `getUserChildren dengan empty list - state tetap ChildrenLoaded dengan list kosong`() = runTest {
        coEvery { getUserChildrenUseCase("user1") } returns Result.Success(emptyList())

        viewModel.getUserChildren("user1")
        advanceUntilIdle()

        val state = viewModel.userChildrenState.value
        assertTrue(state is ChildUiState.ChildrenLoaded)
        assertTrue((state as ChildUiState.ChildrenLoaded).children.isEmpty())
    }

    // ------------------------- GET USER CHILDREN FAILED -------------------------

    @Test
    fun `getUserChildren gagal - state menjadi Error`() = runTest {
        val errorMsg = "Network error"

        coEvery { getUserChildrenUseCase("user1") } returns Result.Error(Exception(errorMsg))

        viewModel.getUserChildren("user1")
        advanceUntilIdle()

        val state = viewModel.userChildrenState.value
        assertTrue(state is ChildUiState.Error)
        assertEquals(errorMsg, (state as ChildUiState.Error).message)
    }

    // ------------------------- RESET STATE -------------------------

    @Test
    fun `resetCreateState - state kembali ke Idle`() = runTest {
        val child = Child(
            id = "child1",
            userId = "user1",
            name = "Anak Test",
            birthDate = "2020-01-15",
            gender = "Male"
        )

        coEvery { createChildUseCase(child) } returns Result.Success("child1")
        coEvery { getUserChildrenUseCase("user1") } returns Result.Success(listOf(child))

        viewModel.createChild(child)
        advanceUntilIdle()

        // Pastikan state sudah berubah
        assertTrue(viewModel.createChildState.value is ChildUiState.Success)

        // Reset
        viewModel.resetCreateState()

        assertEquals(ChildUiState.Idle, viewModel.createChildState.value)
    }

    @Test
    fun `resetChildrenState - state kembali ke Idle`() = runTest {
        val children = listOf(
            Child(id = "c1", userId = "user1", name = "Anak 1", birthDate = "2020-01-01", gender = "Male")
        )

        coEvery { getUserChildrenUseCase("user1") } returns Result.Success(children)

        viewModel.getUserChildren("user1")
        advanceUntilIdle()

        // Pastikan state sudah berubah
        assertTrue(viewModel.userChildrenState.value is ChildUiState.ChildrenLoaded)

        // Reset
        viewModel.resetChildrenState()

        assertEquals(ChildUiState.Idle, viewModel.userChildrenState.value)
    }
}

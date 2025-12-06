package com.example.immunify

import android.util.Log
import com.example.immunify.data.model.ChildData
import com.example.immunify.data.model.Gender
import com.example.immunify.data.model.UserData
import com.example.immunify.domain.usecase.auth.AuthUseCases
import com.example.immunify.ui.auth.AuthViewModel
import com.google.firebase.auth.FirebaseUser
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private lateinit var authUseCases: AuthUseCases
    private lateinit var viewModel: AuthViewModel
    
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        
        // Mock android.util.Log to avoid RuntimeException in unit tests
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
        every { Log.v(any(), any()) } returns 0
        
        authUseCases = mockk(relaxed = true)
        coEvery { authUseCases.getCurrentUser() } returns null

        viewModel = AuthViewModel(authUseCases)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    // ------------------------- INIT -------------------------

    @Test
    fun `init memanggil loadCurrentUser dan mengisi user`() = runTest {
        val fakeUser = UserData(
            id = "1",
            name = "Zhafir",
            email = "test@test.com",
            password = "123456",
            phoneNumber = "08123456789",
            children = emptyList()
        )

        coEvery { authUseCases.getCurrentUser() } returns fakeUser

        val vm = AuthViewModel(authUseCases)
        advanceUntilIdle()

        assertEquals(fakeUser, vm.user.value)
    }

    // ------------------------- LOGIN SUCCESS -------------------------

    @Test
    fun `login berhasil - isLoginSuccess true dan user terisi`() = runTest {
        val fakeUser = UserData(
            id = "1",
            name = "Zhafir",
            email = "test@test.com",
            password = "123456",
            phoneNumber = "08123456789",
            children = listOf(
                ChildData(
                    id = "c1",
                    name = "Anak Pertama",
                    birthDate = "2020-01-01",
                    gender = Gender.MALE,
                    vaccinationHistory = emptyList()
                )
            )
        )

        val fakeFirebaseUser = mockk<FirebaseUser>(relaxed = true)

        coEvery { authUseCases.login("test@test.com", "123456") } returns Result.success(fakeFirebaseUser)
        coEvery { authUseCases.getCurrentUser() } returns fakeUser

        viewModel.login("test@test.com", "123456")
        advanceUntilIdle()

        assertTrue(viewModel.isLoginSuccess.value)
        assertEquals(fakeUser, viewModel.user.value)
    }

    // ------------------------- LOGIN FAILED -------------------------

    @Test
    fun `login gagal - errorMessage terisi dan isLoginSuccess false`() = runTest {
        val errorMsg = "Invalid credentials"

        coEvery {
            authUseCases.login(any(), any())
        } returns Result.failure(Exception(errorMsg))

        viewModel.login("wrong@test.com", "xxx")
        advanceUntilIdle()

        assertFalse(viewModel.isLoginSuccess.value)
        assertEquals(errorMsg, viewModel.errorMessage.value)
    }

    // ------------------------- REGISTER SUCCESS -------------------------

    @Test
    fun `register berhasil - isLoginSuccess true dan user terisi`() = runTest {
        val fakeUser = UserData(
            id = "50",
            name = "Zhafir",
            email = "test@gmail.com",
            password = "123",
            phoneNumber = "0888888888",
            children = emptyList()
        )

        val fakeFirebaseUser = mockk<FirebaseUser>(relaxed = true)


        coEvery {
            authUseCases.register("Zhafir", "tes@gmail.com", "123")
        } returns Result.success(fakeFirebaseUser)

        coEvery { authUseCases.getCurrentUser() } returns fakeUser

        viewModel.register("Zhafir", "tes@gmail.com", "123")
        advanceUntilIdle()

        assertTrue(viewModel.isLoginSuccess.value)
        assertEquals(fakeUser, viewModel.user.value)
    }

    // ------------------------- REGISTER FAILED -------------------------

    @Test
    fun `register gagal - errorMessage terisi`() = runTest {
        val errorMsg = "Email already used"

        coEvery {
            authUseCases.register(any(), any(), any())
        } returns Result.failure(Exception(errorMsg))

        viewModel.register("a", "b@b.com", "c")
        advanceUntilIdle()

        assertEquals(errorMsg, viewModel.errorMessage.value)
        assertFalse(viewModel.isLoginSuccess.value)
    }

    // ------------------------- LOGOUT -------------------------

    @Test
    fun `logout memanggil usecase logout`() = runTest {
        viewModel.logout()

        coVerify { authUseCases.logout() }
    }
}
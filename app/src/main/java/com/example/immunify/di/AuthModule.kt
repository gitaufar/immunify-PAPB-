package com.example.immunify.di

import com.example.immunify.data.firebase.auth.AuthDatasource
import com.example.immunify.data.firebase.auth.FirebaseAuthDatasource
import com.example.immunify.data.repo.AuthRepositoryImpl
import com.example.immunify.domain.repo.AuthRepository
import com.example.immunify.domain.usecase.auth.*
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthDatasource(firebaseAuth: FirebaseAuth): AuthDatasource =
        FirebaseAuthDatasource(firebaseAuth)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authDatasource: AuthDatasource
    ): AuthRepository =
        AuthRepositoryImpl(authDatasource)

    @Provides
    @Singleton
    fun provideAuthUseCases(authRepository: AuthRepository): AuthUseCases =
        AuthUseCases(
            login = LoginUseCase(authRepository),
            register = RegisterUseCase(authRepository),
            logout = LogoutUseCase(authRepository),
            getCurrentUser = GetCurrentUserUseCase(authRepository)
        )
}

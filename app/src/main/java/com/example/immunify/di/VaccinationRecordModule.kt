package com.example.immunify.di

import com.example.immunify.data.firebase.firestore.FirebaseFirestoreDatasource
import com.example.immunify.data.repo.VaccinationRecordRepositoryImpl
import com.example.immunify.domain.repo.VaccinationRecordRepository
import com.example.immunify.domain.usecase.CreateVaccinationRecordUseCase
import com.example.immunify.domain.usecase.GetUserVaccinationRecordsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module untuk VaccinationRecord dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object VaccinationRecordModule {

    @Provides
    @Singleton
    fun provideVaccinationRecordRepository(
        firestoreDatasource: FirebaseFirestoreDatasource
    ): VaccinationRecordRepository {
        return VaccinationRecordRepositoryImpl(firestoreDatasource)
    }

    @Provides
    @Singleton
    fun provideCreateVaccinationRecordUseCase(
        repository: VaccinationRecordRepository
    ): CreateVaccinationRecordUseCase {
        return CreateVaccinationRecordUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserVaccinationRecordsUseCase(
        repository: VaccinationRecordRepository
    ): GetUserVaccinationRecordsUseCase {
        return GetUserVaccinationRecordsUseCase(repository)
    }
}

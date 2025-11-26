package com.example.immunify.di

import com.example.immunify.data.firebase.firestore.FirebaseFirestoreDatasource
import com.example.immunify.data.repo.AppointmentRepositoryImpl
import com.example.immunify.domain.repo.AppointmentRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppointmentModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestoreDatasource(
        firestore: FirebaseFirestore
    ): FirebaseFirestoreDatasource {
        return FirebaseFirestoreDatasource(firestore)
    }

    @Provides
    @Singleton
    fun provideAppointmentRepository(
        firestoreDatasource: FirebaseFirestoreDatasource
    ): AppointmentRepository {
        return AppointmentRepositoryImpl(firestoreDatasource)
    }
    
    @Provides
    @Singleton
    fun provideAppointmentRepositoryImpl(
        firestoreDatasource: FirebaseFirestoreDatasource
    ): AppointmentRepositoryImpl {
        return AppointmentRepositoryImpl(firestoreDatasource)
    }
}

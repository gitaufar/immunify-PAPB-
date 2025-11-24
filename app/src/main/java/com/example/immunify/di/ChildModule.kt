package com.example.immunify.di

import com.example.immunify.data.firebase.firestore.FirebaseFirestoreDatasource
import com.example.immunify.data.repo.ChildRepositoryImpl
import com.example.immunify.domain.repo.ChildRepository
import com.example.immunify.domain.usecase.CreateChildUseCase
import com.example.immunify.domain.usecase.GetUserChildrenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt Module untuk Child dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object ChildModule {

    @Provides
    @Singleton
    fun provideChildRepository(
        firestoreDatasource: FirebaseFirestoreDatasource
    ): ChildRepository {
        return ChildRepositoryImpl(firestoreDatasource)
    }

    @Provides
    fun provideCreateChildUseCase(
        childRepository: ChildRepository
    ): CreateChildUseCase {
        return CreateChildUseCase(childRepository)
    }

    @Provides
    fun provideGetUserChildrenUseCase(
        childRepository: ChildRepository
    ): GetUserChildrenUseCase {
        return GetUserChildrenUseCase(childRepository)
    }
}

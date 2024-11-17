package com.example.authorization.di

import com.example.authorization.data.repository.RegisterRepositoryImpl
import com.example.authorization.domain.repository.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(repositoryImpl: RegisterRepositoryImpl): RegisterRepository
}
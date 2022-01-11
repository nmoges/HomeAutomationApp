package com.homeautomationapp.di

import com.homeautomationapp.database.dao.DeviceDao
import com.homeautomationapp.database.dao.UserDao
import com.homeautomationapp.repositories.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * For repository dependency injection.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(userDao: UserDao, deviceDao: DeviceDao): Repository {
        return Repository(userDao, deviceDao)
    }
}
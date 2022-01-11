package com.homeautomationapp.di

import android.content.Context
import androidx.room.Room
import com.homeautomationapp.database.HomeAutomationDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * For database dependency injection.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): HomeAutomationDb {
        return Room.databaseBuilder(context, HomeAutomationDb::class.java, "home_automation_app_database")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: HomeAutomationDb) = db.userDao

    @Singleton
    @Provides
    fun provideDevicesDao(db: HomeAutomationDb) = db.deviceDao
}
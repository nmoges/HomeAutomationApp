package com.homeautomationapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.homeautomationapp.database.dao.DeviceDao
import com.homeautomationapp.database.dao.UserDao
import com.homeautomationapp.database.entities.DeviceEntity
import com.homeautomationapp.database.entities.UserEntity

/**
 * Application database.
 */
@Database(entities = [UserEntity::class, DeviceEntity::class], version = 1, exportSchema = false)
abstract class HomeAutomationDb: RoomDatabase() {

    abstract val userDao : UserDao
    abstract val deviceDao: DeviceDao
}
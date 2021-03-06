package com.homeautomationapp.database.dao

import androidx.annotation.VisibleForTesting
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.homeautomationapp.database.entities.DeviceEntity

/**
 * DAO interface to access database "devices" table.
 */
@Dao
interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDeviceData(deviceEntity: DeviceEntity): Long

    @Update
    suspend fun updateDeviceData(deviceEntity: DeviceEntity)

    @Delete
    suspend fun deleteDeviceData(deviceEntity: DeviceEntity)

    @Query("SELECT * FROM devices")
    suspend fun getAllDevices(): List<DeviceEntity>

    @RawQuery
    suspend fun getFilteredDevicesData(query: SimpleSQLiteQuery): List<DeviceEntity>

    @VisibleForTesting
    @Query("SELECT * FROM devices WHERE id = :id")
    suspend fun getDeviceById(id: Long): DeviceEntity
}
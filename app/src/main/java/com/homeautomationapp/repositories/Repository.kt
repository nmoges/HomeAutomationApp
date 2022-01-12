package com.homeautomationapp.repositories

import android.content.Context
import androidx.sqlite.db.SimpleSQLiteQuery
import com.homeautomationapp.*
import com.homeautomationapp.database.dao.DeviceDao
import com.homeautomationapp.database.dao.UserDao
import com.homeautomationapp.database.entities.DeviceEntity
import com.homeautomationapp.database.entities.UserEntity
import com.homeautomationapp.model.Device
import com.homeautomationapp.model.User
import com.homeautomationapp.service.FakeDevicesService

/**
 * Defines a database repository access.
 */
class Repository(
    private val userDao: UserDao,
    private val deviceDao: DeviceDao
) {
    /**
     * Initializes both database tables with .json file content
     */
    suspend fun initializeDbWithJSONData(context: Context) {
        val rawData = FakeDevicesService.getRawDataFromJsonFile(context)

        // Insert data in "user" table
        insertUserData(rawData.user.toUserEntity())

        // Insert data in "devices" table
        rawData.devices.forEach {
            insertDeviceData(it.toDeviceEntity())
        }
    }

    private suspend fun insertUserData(userEntity: UserEntity) {
         userDao.insertUserData(userEntity)
    }

     suspend fun updateUserData(user: User) {
         userDao.updateUserData(user.toUserEntity())
     }

     suspend fun getUser(): User {
         return userDao.getUserData().toUser()
     }

     private suspend fun insertDeviceData(deviceEntity: DeviceEntity) {
         deviceDao.insertDeviceData(deviceEntity)
     }

     suspend fun updateDeviceData(device: Device) {
         when (device) {
             is Device.Heater -> {
                 deviceDao.updateDeviceData(device.toDeviceEntity())
             }
             is Device.Light -> {
                 deviceDao.updateDeviceData(device.toDeviceEntity())
             }
             is Device.RollerShutter -> {
                 deviceDao.updateDeviceData(device.toDeviceEntity())
             }
         }
     }

     suspend fun deleteDeviceData(device: Device) {
         when (device) {
             is Device.Heater -> {
                 deviceDao.deleteDeviceData(device.toDeviceEntity())
             }
             is Device.Light -> {
                 deviceDao.deleteDeviceData(device.toDeviceEntity())
             }
             is Device.RollerShutter -> {
                 deviceDao.deleteDeviceData(device.toDeviceEntity())
             }
         }
     }

    /**
     * Retrieves  information from database as a list of [Device].
     */
     suspend fun getAllDevices(): List<Device> {
         val listDevices: MutableList<Device> = mutableListOf()
         deviceDao.getAllDevices().forEach { deviceData ->
             when(deviceData.productType) {
                 "Heater" -> {
                     val heater = deviceData.toHeater()
                     listDevices.add(heater)
                 }
                 "Light" -> {
                     val light = deviceData.toLight()
                     listDevices.add(light)
                 }
                 "RollerShutter" -> {
                     val rollerShutter = deviceData.toShutterRoller()
                     listDevices.add(rollerShutter)
                 }
             }
         }
         return listDevices
     }

    /**
     * Defines a SQL query to perform filter requests in database.
     * @param filters : list of filters status
     */
    suspend fun getFilteredDevices(filters: BooleanArray): List<Device> {
        val listDevices: MutableList<Device> = mutableListOf()
        var query = "SELECT * FROM devices WHERE "

        // Get the list of filters to apply
        val listFilters = mutableListOf<String>()
        filters.forEachIndexed { index, status ->
            if (status) {
                when (index) {
                    0 -> { listFilters.add("Light") }
                    1 -> { listFilters.add("Heater")}
                    2 -> { listFilters.add("RollerShutter")}
                }
            }
        }

        // Define SQL query
        var index = 0
        if (listFilters.size == 0) return listDevices
        else {
            while (index < listFilters.size) {
                query += "devices.product_type = '${listFilters[index]}' "
                index++
                if (index < listFilters.size) query += "OR "
            }

            deviceDao.getFilteredDevicesData(SimpleSQLiteQuery(query)).forEach { deviceData ->
                when(deviceData.productType) {
                    "Heater" -> {
                        val heater = deviceData.toHeater()
                        listDevices.add(heater)
                    }
                    "Light" -> {
                        val light = deviceData.toLight()
                        listDevices.add(light)
                    }
                    "RollerShutter" -> {
                        val rollerShutter = deviceData.toShutterRoller()
                        listDevices.add(rollerShutter)
                    }
                }
            }
            return listDevices
        }
    }
}
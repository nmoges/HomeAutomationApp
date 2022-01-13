package com.homeautomationapp

import com.homeautomationapp.database.HomeAutomationDb
import com.homeautomationapp.model.Address
import com.homeautomationapp.model.Device
import com.homeautomationapp.model.User
import com.homeautomationapp.repositories.Repository
import com.homeautomationapp.utils.toDeviceEntity
import com.homeautomationapp.utils.toUserEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * Instrumentation test for database access.
 * Performed on emulator Pixel 3a XL API 30 :
 *              - Resolution : 1080 x 2160: 400dpi
 *              - API 30
 *              - Target : Android 11.0
 *              - CPU/ABI : x86
 */
@HiltAndroidTest
class DatabaseInstrumentedTest {

    @get: Rule val rule = HiltAndroidRule(this)

    @Inject lateinit var database: HomeAutomationDb
    @Inject lateinit var repository: Repository

    lateinit var user: User
    lateinit var light: Device.Light

    @Before
    fun setUp() {
        rule.inject()
        initializeData()
    }

    @After
    fun closeDatabase() {
        database.apply {
            clearAllTables()
            close()
        }
    }

    /**
     * Tests the following operation on the table "user" :
     * - Insertion
     * - Update
     */
    @Test
    fun test_insert_and_update_user_in_database() {
        runBlocking {
            // Insert
            val id = repository.insertUserData(user.toUserEntity())
            assertEquals(1, id)
            // Read user
            var userFromDb = repository.getUser()
            assertEquals("John", userFromDb.firstName)
            assertEquals("Doe", userFromDb.lastName)
            assertEquals("15/10/1995", userFromDb.birthdate)
            assertEquals("johndoe@gmail.com", userFromDb.email)
            assertEquals("0606060606", userFromDb.phone)
            assertEquals("2B", userFromDb.address.streetNumber)
            assertEquals("rue Michelet", userFromDb.address.streetName)
            assertEquals(92130, userFromDb.address.postalCode)
            assertEquals("Issy-les-Moulineau", userFromDb.address.city)
            assertEquals("France", userFromDb.address.country)
            // Update user
            user.firstName = "Jane"
            repository.updateUserData(user)
            // Read user
            userFromDb = repository.getUser()
            assertEquals("Jane", userFromDb.firstName)
        }
    }

    /**
     * Tests the following operation on the table "devices" :
     * - Insertion
     * - Update
     * - Read
     * - Remove
     */
    @Test
    fun test_insert_update_read_delete_device_in_database() {
        runBlocking {
            // Insert
            val idLight = repository.insertDeviceData(light.toDeviceEntity())
            assertEquals(1, idLight)
            // Update
            light.mode = "OFF"
            repository.updateDeviceData(light)
            // Read
            val readLight = repository.getDeviceById(idLight)
            assertEquals("OFF", readLight.mode)
            // Delete
            repository.deleteDeviceData(light)
            assertNull(repository.getDeviceById(idLight))
        }
    }

    private fun initializeData() {
        val address = Address(streetName = "rue Michelet", streetNumber = "2B", postalCode = 92130,
                              city = "Issy-les-Moulineau", country = "France")

        user = User(id = 1, firstName = "John", lastName = "Doe", birthdate = "15/10/1995",
                    email = "johndoe@gmail.com", phone = "0606060606", address = address)

        light = Device.Light(id = 1, deviceName = "Lampe - Cuisine", productType = "Light",
                             intensity = 50, mode = "ON")
    }
}
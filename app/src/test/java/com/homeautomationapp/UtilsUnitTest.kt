package com.homeautomationapp

import com.homeautomationapp.database.entities.DeviceEntity
import com.homeautomationapp.database.entities.UserEntity
import com.homeautomationapp.model.Device
import com.homeautomationapp.service.RawAddress
import com.homeautomationapp.service.RawDevice
import com.homeautomationapp.service.RawUser
import com.homeautomationapp.utils.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.jupiter.api.Test

/**
 * Unit tests for "utils" package
 */
class UtilsUnitTest {

    /**
     * Tests [DateFormatter].
     */
    @Test
    fun test_date_formatter() {
        assertEquals("25/12/2020", DateFormatter.getDate(2020, 11, 25))
        assertEquals("01/02/2020", DateFormatter.getDate(2020, 1, 1))
    }

    /**
     * Tests [NameDeviceComparator].
     */
    @Test
    fun test_name_device_comparator() {
        val light = Device.Light(id = 1, deviceName = "Lampe - Cuisine", productType = "Light",
                                 luminosity = 50, mode = "ON")

        val heater = Device.Heater(id = 1, deviceName = "Radiateur - Chambre", productType = "Heater",
                                  temperature = 20, mode = "ON")

        val rollerShutter = Device.RollerShutter(id = 1, deviceName = "Volet roulant",
                                                 productType = "RollerShutter", position = 15)
        assert(NameDeviceComparator.compare(light, heater) < 0)
        assert(NameDeviceComparator.compare(heater, light) > 0)
        assert(NameDeviceComparator.compare(light, rollerShutter) < 0)
        assert(NameDeviceComparator.compare(light, light) == 0)
    }

    /**
     * Tests DeviceEntity.toHeater() and Device.Heater.toDeviceEntity() converters
     */
    @Test
    fun test_device_entity_heater_converters() {
        // DeviceEntity.toHeater()
        var deviceEntity = DeviceEntity(
            id = 9,
            name = "Radiateur - Salle de bain",
            mode = "OFF",
            temperature = 20,
            productType = "Heater",
            intensity = null,
            position = null
        )
        val heater = deviceEntity.toHeater()
        assertEquals(9, heater.id)
        assertEquals("Radiateur - Salle de bain", heater.deviceName)
        assertEquals("OFF", heater.mode)
        assertEquals(20, heater.temperature)
        assertEquals("Heater", heater.productType)

        // Device.Heater.toDeviceEntity()
        deviceEntity = heater.toDeviceEntity()
        assertEquals(9, deviceEntity.id)
        assertEquals("Radiateur - Salle de bain", deviceEntity.name)
        assertEquals("OFF", deviceEntity.mode)
        assertEquals(20, deviceEntity.temperature)
        assertEquals("Heater", deviceEntity.productType)
        assertNull(deviceEntity.intensity)
        assertNull(deviceEntity.position)
    }

    /**
     * Tests DeviceEntity.toLight() and Device.Light.toDeviceEntity() converters
     */
    @Test
    fun test_device_entity_light_converters() {
        // DeviceEntity.toLight()
        var deviceEntity = DeviceEntity(
            id = 10,
            name = "Lampe - Salle de bain",
            intensity = 36,
            mode = "ON",
            productType = "Light",
            temperature = null,
            position = null
        )
        val light = deviceEntity.toLight()
        assertEquals(10, light.id)
        assertEquals("Lampe - Salle de bain", light.deviceName)
        assertEquals("ON", light.mode)
        assertEquals(36, light.luminosity)
        assertEquals("Light", light.productType)

        // Device.Light.toDeviceEntity()
        deviceEntity = light.toDeviceEntity()
        assertEquals(10, deviceEntity.id)
        assertEquals("Lampe - Salle de bain", deviceEntity.name)
        assertEquals("ON", deviceEntity.mode)
        assertEquals(36, deviceEntity.intensity)
        assertEquals("Light", deviceEntity.productType)
        assertNull(deviceEntity.temperature)
        assertNull(deviceEntity.position)
    }

    /**
     * Tests DeviceEntity.toShutterRoller() and Device.RollerShutter.toDeviceEntity() converters
     */
    @Test
    fun test_device_entity_shutter_roller_converters() {
        // DeviceEntity.toShutterRoller()
        var deviceEntity = DeviceEntity(
            id = 11,
            name = "Volet roulant",
            intensity = null,
            mode = null,
            productType = "RollerShutter",
            temperature = null,
            position = 33
        )
        val rollerShutter = deviceEntity.toShutterRoller()
        assertEquals(11, rollerShutter.id)
        assertEquals("Volet roulant", rollerShutter.deviceName)
        assertEquals("RollerShutter", rollerShutter.productType)
        assertEquals(33, rollerShutter.position)

        // Device.RollerShutter.toDeviceEntity()
        deviceEntity = rollerShutter.toDeviceEntity()
        assertEquals(11, deviceEntity.id)
        assertEquals("Volet roulant", deviceEntity.name)
        assertEquals("RollerShutter", deviceEntity.productType)
        assertEquals(33, deviceEntity.position)
        assertNull(deviceEntity.mode)
        assertNull(deviceEntity.intensity)
        assertNull(deviceEntity.temperature)
    }

    /**
     * Tests UserEntity.toUser() and Device.User.toUserEntity() converters
     */
    @Test
    fun test_user_entity_user_converters() {
        // UserEntity.toUser()
        var userEntity = UserEntity(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            birthdate = "15/10/1995",
            email = "johndoe@gmail.com",
            phone = "0606060606",
            streetNumber = "2B",
            streetName = "rue Michelet",
            postalCode = 92130,
            city = "Issy-les-Moulineau",
            country = "France"
        )
        val user = userEntity.toUser()
        assertEquals(1, user.id)
        assertEquals("John", user.firstName)
        assertEquals("Doe", user.lastName)
        assertEquals("15/10/1995", user.birthdate)
        assertEquals("johndoe@gmail.com", user.email)
        assertEquals("0606060606", user.phone)
        assertEquals("2B", user.address.streetNumber)
        assertEquals("rue Michelet", user.address.streetName)
        assertEquals(92130, user.address.postalCode)
        assertEquals("Issy-les-Moulineau", user.address.city)
        assertEquals("France", user.address.country)

        // Device.User.toUserEntity()
        userEntity = user.toUserEntity()
        assertEquals(1, userEntity.id)
        assertEquals("John", userEntity.firstName)
        assertEquals("Doe", userEntity.lastName)
        assertEquals("15/10/1995", userEntity.birthdate)
        assertEquals("johndoe@gmail.com", userEntity.email)
        assertEquals("0606060606", userEntity.phone)
        assertEquals("2B", userEntity.streetNumber)
        assertEquals("rue Michelet", userEntity.streetName)
        assertEquals(92130, userEntity.postalCode)
        assertEquals("Issy-les-Moulineau", userEntity.city)
        assertEquals("France", userEntity.country)
    }

    /**
     * Tests RawUser.toUserEntity() converter
     */
    @Test
    fun test_raw_user_to_user_entity_converter() {
        val rawAddress = RawAddress(
            city = "Issy-les-Moulineau",
            country = "France",
            postalCode = 92130,
            street = "rue Michelet",
            streetCode = "2B"
        )
        val rawUser = RawUser(
            address = rawAddress,
            birthDate = 813766371000,
            firstName = "John",
            lastName = "Doe"
        )
        val userEntity = rawUser.toUserEntity()
        assertEquals(0, userEntity.id)
        assertEquals("John", userEntity.firstName)
        assertEquals("Doe", userEntity.lastName)
        assertEquals("15/10/1995", userEntity.birthdate)
        assertEquals("Issy-les-Moulineau", userEntity.city)
        assertEquals("France", userEntity.country)
        assertEquals(92130, userEntity.postalCode)
        assertEquals("rue Michelet", userEntity.streetName)
        assertEquals("2B", userEntity.streetNumber)
    }

    /**
     * Tests RawUser.toDeviceEntity() converter
     */
    @Test
    fun test_raw_device_to_device_entity_converter() {
        val rawDevice = RawDevice(
            id = 6,
            deviceName = "Radiateur - Salon",
            intensity = 0,
            mode = "OFF",
            position = 0,
            productType = "Heater",
            temperature = 18,
        )
        val deviceEntity = rawDevice.toDeviceEntity()
        assertEquals(0, deviceEntity.id) // id auto-incremented when data in stored in db
        assertEquals("Radiateur - Salon", deviceEntity.name)
        assertEquals(null, deviceEntity.intensity)
        assertEquals("OFF", deviceEntity.mode)
        assertEquals(null, deviceEntity.position)
        assertEquals("Heater", deviceEntity.productType)
        assertEquals(18, deviceEntity.temperature)
    }
}
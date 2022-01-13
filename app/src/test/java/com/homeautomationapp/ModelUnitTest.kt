package com.homeautomationapp

import com.homeautomationapp.model.Address
import com.homeautomationapp.model.Device
import com.homeautomationapp.model.User
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito

/**
 * Unit tests for "model" package.
 */
class ModelUnitTest {

    /**
     * Tests [User] class.
     */
    @Test
    fun test_user_class() {
        val address = Mockito.mock(Address::class.java)
        Mockito.`when`(address.streetNumber).thenReturn("2B")
        Mockito.`when`(address.streetName).thenReturn("rue Michelet")
        Mockito.`when`(address.postalCode).thenReturn(92130)
        Mockito.`when`(address.city).thenReturn("Issy-les-Moulineaux")
        Mockito.`when`(address.country).thenReturn("France")

        val user = User(
            id = 1,
            firstName = "John",
            lastName = "Doe",
            birthdate = "15/10/1995",
            email = "johndoe@gmail.com",
            phone = "0606060606",
            address = address
        )
        assertEquals(1, user.id)
        assertEquals("John", user.firstName)
        assertEquals("Doe", user.lastName)
        assertEquals("15/10/1995", user.birthdate)
        assertEquals("johndoe@gmail.com", user.email)
        assertEquals("0606060606", user.phone)
        assertEquals("2B", user.address.streetNumber)
        assertEquals("rue Michelet", user.address.streetName)
        assertEquals(92130, user.address.postalCode)
        assertEquals("Issy-les-Moulineaux", user.address.city)
        assertEquals("France", user.address.country)
    }

    /**
     * Tests [Address] class.
     */
    @Test
    fun test_address_class() {
        val address = Address(
            streetName = "rue Michelet",
            streetNumber = "2B",
            postalCode = 92130,
            city = "Issy-les-Moulineaux",
            country = "France"
        )
        assertEquals("rue Michelet", address.streetName)
        assertEquals("2B", address.streetNumber)
        assertEquals(92130, address.postalCode)
        assertEquals("Issy-les-Moulineaux", address.city)
        assertEquals("France", address.country)
    }

    /**
     * Tests [Device.Heater] class.
     */
    @Test
    fun test_heater_class() {
        val heater = Device.Heater(
            id = 1,
            deviceName = "Radiateur - Salle de bain",
            productType = "Heater",
            temperature = 20,
            mode = "OFF",
        )
        assertEquals(1, heater.id)
        assertEquals("Radiateur - Salle de bain", heater.deviceName)
        assertEquals("Heater", heater.productType)
        assertEquals(20, heater.temperature)
        assertEquals("OFF", heater.mode)
    }

    /**
     * Tests [Device.Light] class.
     */
    @Test
    fun test_light_class() {
        val light = Device.Light(
            id = 1,
            deviceName = "Lampe - Salle de bain",
            productType = "Light",
            intensity = 36,
            mode = "ON",
        )
        assertEquals(1, light.id)
        assertEquals("Lampe - Salle de bain", light.deviceName)
        assertEquals("Light", light.productType)
        assertEquals(36, light.intensity)
        assertEquals("ON", light.mode)
    }

    /**
     * Tests [Device.RollerShutter] class.
     */
    @Test
    fun test_roller_shutter_class() {
        val rollerShutter = Device.RollerShutter(
            id = 1,
            deviceName = "Volet roulant - Salle de bain",
            productType = "RollerShutter",
            position = 70
        )
        assertEquals(1, rollerShutter.id)
        assertEquals("Volet roulant - Salle de bain", rollerShutter.deviceName)
        assertEquals("RollerShutter", rollerShutter.productType)
        assertEquals(70, rollerShutter.position)
    }
}
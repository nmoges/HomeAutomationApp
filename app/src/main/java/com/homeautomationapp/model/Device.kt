package com.homeautomationapp.model

/**
 * Defines all types of devices.
 */
sealed class Device(val deviceName: String, val productType: String) {
    class Heater(
        deviceName: String,
        productType: String,
        val temperature: Int?,
        val mode: String?): Device(deviceName, productType)

    class RollerShutter(
        deviceName: String,
        productType: String,
        val position: Int?): Device(deviceName, productType)

    class Light(
        deviceName: String,
        productType: String,
        val luminosity: Int?,
        val mode: String?): Device(deviceName, productType)
}
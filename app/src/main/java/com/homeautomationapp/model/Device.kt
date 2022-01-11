package com.homeautomationapp.model

/**
 * Defines all types of devices.
 */
sealed class Device(val id: Int = 1, val deviceName: String, val productType: String) {
    class Heater(
        id: Int,
        deviceName: String,
        productType: String,
        var temperature: Int?,
        var mode: String?): Device(id,deviceName, productType)

    class RollerShutter(
        id: Int,
        deviceName: String,
        productType: String,
        var position: Int?): Device(id, deviceName, productType)

    class Light(
        id: Int,
        deviceName: String,
        productType: String,
        var luminosity: Int?,
        var mode: String?): Device(id, deviceName, productType)
}
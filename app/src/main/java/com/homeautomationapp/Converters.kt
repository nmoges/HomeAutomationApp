package com.homeautomationapp

import com.homeautomationapp.database.entities.DeviceEntity
import com.homeautomationapp.database.entities.UserEntity
import com.homeautomationapp.model.Address
import com.homeautomationapp.model.Device
import com.homeautomationapp.model.User
import com.homeautomationapp.service.RawDevice
import com.homeautomationapp.service.RawUser

fun DeviceEntity.toHeater() = Device.Heater(
    id = this.id,
    deviceName = this.name,
    productType = this.productType,
    temperature = this.temperature,
    mode = this.mode
)

fun DeviceEntity.toLight() = Device.Light(
    id = this.id,
    deviceName = this.name,
    productType = this.productType,
    luminosity = this.intensity,
    mode = this.mode
)

fun DeviceEntity.toShutterRoller() = Device.RollerShutter(
    id = this.id,
    deviceName = this.name,
    productType = this.productType,
    position = this.position
)

fun Device.Heater.toDeviceEntity() = DeviceEntity(
    id = this.id,
    name = this.deviceName,
    intensity = null,
    mode = this.mode,
    position = null,
    productType = this.productType,
    temperature = this.temperature
)

fun Device.Light.toDeviceEntity() = DeviceEntity(
    id = this.id,
    name = this.deviceName,
    intensity = this.luminosity,
    mode = this.mode,
    position = null,
    productType = this.productType,
    temperature = null
)

fun Device.RollerShutter.toDeviceEntity() = DeviceEntity(
    id = this.id,
    name = this.deviceName,
    intensity = null,
    mode = null,
    position = this.position,
    productType = this.productType,
    temperature = null
)

fun UserEntity.toUser(): User {
    val address = Address(
        streetName = this.streetName,
        streetNumber = this.streetNumber,
        postalCode = this.postalCode,
        city = this.city,
        country = this.country
    )
    return User(
        firstName = this.firstName,
        lastName = this.lastName,
        birthdate = this.birthdate,
        email = this.email,
        phone = this.phone,
        address = address
    )
}

fun User.toUserEntity() = UserEntity(
    id = 1,
    firstName = this.firstName,
    lastName = this.lastName,
    birthdate = this.birthdate,
    email = this.email,
    phone = this.phone,
    streetNumber = this.address.streetNumber,
    streetName = this.address.streetName,
    postalCode = this.address.postalCode,
    city = this.address.city,
    country = this.address.country
)

fun RawUser.toUserEntity() = UserEntity(
    firstName = this.firstName,
    lastName = this.lastName,
    birthdate = "",
    email = "",
    phone = "",
    streetNumber = this.address.streetCode,
    streetName = this.address.street,
    postalCode = this.address.postalCode,
    city = this.address.city,
    country = this.address.country
)

fun RawDevice.toDeviceEntity() = DeviceEntity(
    name = this.deviceName,
    intensity = if (this.productType == "Light") this.intensity else null,
    mode = if (this.productType == "Light" || this.productType == "Heater") this.mode else null,
    position = if (this.productType == "RollerShutter") this.position else null,
    productType = this.productType,
    temperature = if (this.productType == "Heater") this.temperature else null
)
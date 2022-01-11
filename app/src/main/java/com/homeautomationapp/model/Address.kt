package com.homeautomationapp.model

/**
 * Defines a [User] address.
 */
class Address(
    var streetName: String,
    var streetNumber: String,
    var postalCode: Int,
    var city: String,
    var country: String,
)
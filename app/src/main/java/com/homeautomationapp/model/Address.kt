package com.homeautomationapp.model

/**
 * Defines a [User] address.
 */
class Address(
    val streetName: String,
    val streetNumber: String,
    val postalCode: Int,
    val city: String,
    val country: String,
)
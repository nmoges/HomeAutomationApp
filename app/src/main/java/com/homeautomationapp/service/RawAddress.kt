package com.homeautomationapp.service

/**
 * Defines user address information extracted from a .json file.
 */
class RawAddress(
    val city: String,
    val country: String,
    val postalCode: Int,
    val street: String,
    val streetCode: String
)
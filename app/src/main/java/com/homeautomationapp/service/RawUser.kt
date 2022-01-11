package com.homeautomationapp.service

/**
 * Defines user profile information extracted from a .json file.
 */
class RawUser(
    val address: RawAddress,
    val birthDate: Long,
    val firstName: String,
    val lastName: String
)
package com.homeautomationapp.model

/**
 * Defines a user profile.
 */
class User(
    val id: Int,
    var firstName: String,
    var lastName: String,
    var birthdate: String,
    var email: String,
    var phone: String,
    var address: Address,
)
package com.homeautomationapp.service

/**
 * Defines data information extracted from a .json file.
 */
data class RawData(
    val devices: List<RawDevice>,
    val user: RawUser
)
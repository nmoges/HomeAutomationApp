package com.homeautomationapp.service

/**
 * Defines device information extracted from a .json file.
 */
data class RawDevice(
    val deviceName: String,
    val id: Int,
    val intensity: Int,
    val mode: String,
    val position: Int,
    val productType: String,
    val temperature: Int
)
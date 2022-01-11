package com.homeautomationapp.utils

import com.homeautomationapp.model.Device

/**
 * Comparator for alphabetical comparison between devices names.
 */
object NameDeviceComparator: Comparator<Device> {
    override fun compare(firstDevice: Device?, secondDevice: Device?): Int {
        var compare = 0
        if (firstDevice != null && secondDevice != null)
            compare = firstDevice.deviceName.uppercase().compareTo(secondDevice.deviceName.uppercase())
        return compare
    }
}
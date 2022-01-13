package com.homeautomationapp

import androidx.test.rule.ActivityTestRule
import com.homeautomationapp.service.FakeDevicesService
import com.homeautomationapp.ui.activities.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumentation test for FakeDevicesService class.
 * Performed on emulator Pixel 3a XL API 30 :
 *              - Resolution : 1080 x 2160: 400dpi
 *              - API 30
 *              - Target : Android 11.0
 *              - CPU/ABI : x86
 */
@HiltAndroidTest
class ServiceInstrumentedTest {

    @get: Rule(order = 0) val hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    var mMainActivityRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var mainActivity: MainActivity

    @Before
    fun setUp() {
        hiltRule.inject()
        mainActivity = mMainActivityRule.activity
    }

    /**
     * Tests if service provides data after reading the .json file.
     */
    @Test
    fun test_fake_devices_service() {
       val rawData = FakeDevicesService.getRawDataFromJsonFile(mainActivity.applicationContext)
        assertNotNull(rawData)
        assertNotNull(rawData.devices)
        assertNotNull(rawData.user)
        assertEquals(12, rawData.devices.size)
    }
}
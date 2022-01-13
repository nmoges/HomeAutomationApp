package com.homeautomationapp

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.homeautomationapp.ui.activities.MainActivity
import com.homeautomationapp.ui.adapters.ListDevicesAdapter
import com.homeautomationapp.ui.fragments.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumentation test for fragments UI.
 * Performed on emulator Pixel 3a XL API 30 :
 *              - Resolution : 1080 x 2160: 400dpi
 *              - API 30
 *              - Target : Android 11.0
 *              - CPU/ABI : x86
 */
@HiltAndroidTest
class NavInstrumentedTest {

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
     * Tests if fragment [ListDevicesFragment] is correctly displayed at launch.
     */
    @Test
    fun test_if_fragment_list_devices_is_displayed() {
        onView(withId(R.id.fragment_list_devices)).check(matches(isDisplayed()))
    }

    /**
     * Tests if fragment [ManagerFragment] is correctly displayed after a click on
     * "Manager devices" menu icon.
     */
    @Test
    fun test_if_fragment_manager_is_displayed() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)

        onView(withText(mainActivity.resources.getString(R.string.menu_item_manage_devices)))
            .perform(click())

        onView(withId(R.id.fragment_devices_manager)).check(matches(isDisplayed()))
    }

    /**
     * Tests if fragment [UserProfileFragment] is correctly displayed after a click on
     * "User profile" menu item.
     */
    @Test
    fun test_if_fragment_user_profile_is_displayed() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)

        onView(withText(mainActivity.resources.getString(R.string.menu_item_user_profile)))
            .perform(click())

        onView(withId(R.id.fragment_user_device)).check(matches(isDisplayed()))
    }

    /**
     * Checks if dialog filter is displayed after a click on "Filters" menu icon.
     */
    @Test
    fun test_if_dialog_filter_is_displayed() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)

        onView(withText(mainActivity.resources.getString(R.string.menu_item_filter)))
            .perform(click())

        onView(withText(mainActivity.resources.getString(R.string.title_dialog_filter)))
            .check(matches(isDisplayed()))

        pressBack()
    }

    /**
     * Checks if recycler view correctly displays the list of devices.
     */
    @Test
    fun test_if_list_of_devices_is_not_empty() {
        onView(withId(R.id.recycler_view_list_devices)).check(matches(hasMinimumChildCount(1)))
    }

    /**
     * Tests if fragment [LightsFragment] is correctly displayed after a click on a recycler view item.
     */
    @Test
    fun test_if_fragment_lights_is_displayed() {
        // Click on item n°1 on recycler view (light)
        onView(withId(R.id.recycler_view_list_devices))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<ListDevicesAdapter.ListDevicesViewHolder>(0, click()))

        onView(withId(R.id.fragment_lights)).check(matches(isDisplayed()))
    }

    /**
     * Tests if fragment [HeatersFragment] is correctly displayed after a click on a recycler
     * view item.
     */
    @Test
    fun test_if_fragment_heaters_is_displayed() {
        // Click on item n°5 on recycler view (heater)
        onView(withId(R.id.recycler_view_list_devices))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ListDevicesAdapter
                                        .ListDevicesViewHolder>(4, click()))

        onView(withId(R.id.fragment_heaters)).check(matches(isDisplayed()))
    }

    /**
     * Tests if fragment [RollerShuttersFragment] is correctly displayed after a click on a recycler
     * view item.
     */
    @Test
    fun test_if_fragment_roller_shutters_is_displayed() {
        // Click on item n°5 on recycler view (heater)
        onView(withId(R.id.recycler_view_list_devices))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ListDevicesAdapter
                                        .ListDevicesViewHolder>(8, click()))

        onView(withId(R.id.fragment_roller_shutters)).check(matches(isDisplayed()))
    }
}
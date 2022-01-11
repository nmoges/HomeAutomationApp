package com.homeautomationapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Defines an application entrypoint for Hilt dependency injection.
 */
@HiltAndroidApp
class BaseApplication: Application()
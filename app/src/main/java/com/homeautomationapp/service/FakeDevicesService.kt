package com.homeautomationapp.service

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

/**
 * Defines a fake service providing data from a predefined .json file.
 */
object FakeDevicesService {

    fun getRawDataFromJsonFile(context: Context): RawData {
        val type = object : TypeToken<RawData>() {}.type
        return Gson().fromJson(getJsonDataFromFile(context, "data.json"), type)
    }

    private fun getJsonDataFromFile(context: Context, fileName: String): String? {
        val jsonData: String
        try {
            jsonData = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            Log.w("WARNING", "Exception", ioException)
            return null
        }
        return jsonData
    }
}
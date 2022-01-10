package com.homeautomationapp.utils

object DateFormatter {

    fun getDate(year: Int, month: Int, day: Int): String {
        val dayFormat =  if (day < 10) "0$day" else "$day"
        val monthFormat = if (month+1 < 10) "0${month+1}" else "${month+1}"
        return "$dayFormat/$monthFormat/$year"
    }
}
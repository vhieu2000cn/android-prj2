package com.udacity.asteroidradar.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getTodayString(): String {
    val calendar = Calendar.getInstance()
    return dateToString(calendar.time)
}

fun getAfterSevenDaysString(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    return dateToString(calendar.time)
}

private fun dateToString(date: Date): String =
    SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault()).format(date)

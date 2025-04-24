package com.onedev.newsapptest.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun getGreeting(): String {
    val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 0..11 -> "Good Morning"
        in 12..15 -> "Good Afternoon"
        in 16..20 -> "Good Evening"
        else -> "Good Night"
    }
}

fun String.toFormattedDate(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    parser.timeZone = TimeZone.getTimeZone("UTC")
    val date = parser.parse(this)
    val formatter = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("id"))
    return date?.let { formatter.format(it) } ?: ""
}

fun String.extractSummary(): String {
    return this.split(".").firstOrNull()?.plus(".") ?: ""
}
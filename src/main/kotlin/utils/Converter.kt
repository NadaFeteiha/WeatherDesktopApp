package utils

import java.util.Date
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

fun getTimeNowHourOnlyAsInt(): Int {
    val dateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH")
    val hour = dateTime.format(formatter).toInt()
    return hour
}

fun convertTimeToHourAMPM(timeString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val dateTime = LocalDateTime.parse(timeString, formatter)
    val hour = dateTime.hour
    val amPm = if (hour < 12) "AM" else "PM"
    val currentHour = if (hour <= 12) {
        hour
    } else {
        hour - 12
    }
    return "$currentHour $amPm"
}
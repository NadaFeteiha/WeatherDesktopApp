package utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import java.text.SimpleDateFormat
import java.util.*

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

fun convertDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM, yyyy h:mm a", Locale.getDefault())

    val parsedDate = inputFormat.parse(inputDate)
    return outputFormat.format(parsedDate)
}

fun getHourNow(inputDate: String): Int {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat("h", Locale.getDefault())
    val outputFormat2 = SimpleDateFormat("a", Locale.getDefault())

    var time = outputFormat.format(inputFormat.parse(inputDate)).toInt()

    if (outputFormat2.format(inputFormat.parse(inputDate)) == "PM") {
        time = time + 12
    }
    return time
}
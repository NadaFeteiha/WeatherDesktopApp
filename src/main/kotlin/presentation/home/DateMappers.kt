package presentation.home

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

    if (outputFormat2.format(inputFormat.parse(inputDate)) == "PM"
        && time>12) {
        time = time + 12
    }
    return time
}

fun timeToMilliseconds(timeStr: String): Long {
    val dateFormat = SimpleDateFormat("hh:mm a")
    val time = dateFormat.parse(timeStr)

    if (time != null) {
        val calendar = java.util.Calendar.getInstance()
        val currentTime = java.util.Calendar.getInstance()

        calendar.time = time

        currentTime.set(java.util.Calendar.HOUR_OF_DAY, calendar.get(java.util.Calendar.HOUR_OF_DAY))
        currentTime.set(java.util.Calendar.MINUTE, calendar.get(java.util.Calendar.MINUTE))
        currentTime.set(java.util.Calendar.SECOND, 0)
        currentTime.set(java.util.Calendar.MILLISECOND, 0)

        return currentTime.timeInMillis
    }
    return 0L
}

fun convertDateToMilliseconds(dateTimeString: String): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val date = dateFormat.parse(dateTimeString)
    return date?.time ?: -1L
}
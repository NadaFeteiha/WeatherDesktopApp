package viewModel

import data.remote.dto.Hour
import data.remote.dto.Weather
import java.util.Date

fun Weather.toUIState(): HomeUIState {
    return HomeUIState(
        forecastHourly = forecast.forecastday[0].hour?.subList(getCurrentHour() + 3, 24)
            ?.mapIndexed { index, hour ->
                hour.toUIState(index)
            } ?: emptyList(),
        windDegree = (current?.windDegree ?: 0).toFloat(),
        windKph = current?.windKph ?: 0.0,
        humidityValue = "${current?.humidity}%",
        humidityDescription = "Normal",
        visibilityAvg = "${current?.visKm} Km",
        feelsLike = "${current?.feelslikeC}",
        feelDescription = current?.condition?.text ?: ""
    )
}

fun Hour.toUIState(index: Int) = ForecastHour(
    time = if (index == 0) {
        "Now"
    } else {
        convertEpochToTimeString(timeEpoch)
    },
    icon = condition?.icon ?: "",
    temp = tempC ?: 0.0
)

fun getCurrentHour(): Int {
    val currentTime = Date()
    val currentMillis = currentTime.time
    val offsetMillis = currentTime.timezoneOffset * 60 * 1000
    val currentHour = ((currentMillis + offsetMillis) / (1000 * 60 * 60)) % 24
    return currentHour.toInt()
}

fun convertEpochToTimeString(epoch: Long): String {
    val hours = (epoch / 3600) % 24 // Calculate hours
    val amPm = if (hours >= 12) "PM" else "AM" // Determine AM/PM

    val formattedHours = when (hours % 12) {
        0L -> 12
        else -> hours % 12
    }

    return String.format("%d %s", formattedHours, amPm)
}
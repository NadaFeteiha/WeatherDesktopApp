package viewModel

import data.remote.dto.Forecastday
import data.remote.dto.Hour
import data.remote.dto.Weather
import io.ktor.http.HttpHeaders.Date
import utils.convertDate
import utils.convertDateToMilliseconds
import utils.convertTimeToHourAMPM
import utils.getHourNow
import java.text.SimpleDateFormat
import java.util.*

fun Weather.toUIState(): HomeUIState {
    return HomeUIState(
        forecastHourly = forecast.forecastday[0].hour?.subList(getHourNow(location?.localtime ?: ""), 24)
            ?.mapIndexed { index, hour ->
                hour.toUIState(index)
            } ?: emptyList(),
        windDegree = (current?.windDegree ?: 0).toFloat(),
        windKph = current?.windKph ?: 0.0,
        humidityValue = "${current?.humidity}%",
        humidityDescription = "Normal",
        visibilityAvg = "${current?.visKm} Km",
        feelsLike = "${current?.feelslikeC}",
        feelDescription = current?.condition?.text ?: "",
        date = convertDate(location?.localtime ?: ""),
        temperature = current?.tempC.toString(),
        cityName = location?.name ?: "",
        countryName = location?.country ?: "",
        icon = current?.condition?.icon ?: "",
        uvValue = (current?.uv?.toInt() ?: 0) * 10,
        uvIndexDescription = getUvIndexDescription(current?.uv ?: 0.0),
        daysForecastUiState = forecast.forecastday.toUIState(),
        sunSet = forecast.forecastday[0].astro?.sunset!!,
        sunRise = forecast.forecastday[0].astro?.sunrise!!,
        currentTime = convertDateToMilliseconds(location?.localtime?:"")
    )
}

fun Hour.toUIState(index: Int) = ForecastHour(
    time = if (index == 0) {
        "Now"
    } else {
        convertTimeToHourAMPM(time)
    },
    icon = condition?.icon ?: "",
    temp = tempC ?: 0.0
)

fun List<Forecastday>.toUIState(): List<DayForecastUiState> {
    return mapIndexed { index, forecastday ->
        DayForecastUiState(
            day = convertEpochMillisecondsToDate(forecastday.dateEpoch ?: 0),
            iconUrl = forecastday.day?.condition?.icon ?: "",
            minTemperature = (forecastday.day?.mintempC ?: 0.0).toString(),
            maxTemperature = (forecastday.day?.maxtempC ?: 0.0).toString(),
            dateOfDay = if (index == 0) {
                "Today"
            } else {
                getDayOfWeek(forecastday.dateEpoch ?: 0)
            }
        )
    }

}

private fun convertEpochMillisecondsToDate(epochMilliseconds: Int): String {
    val date = Date(epochMilliseconds * 1000L)
    val sdf = SimpleDateFormat("dd MMM", Locale.ENGLISH)
    return sdf.format(date)
}

private fun getDayOfWeek(timestamp: Int): String {
    return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
}


private fun getUvIndexDescription(uvIndex: Double): String {
    return when {
        uvIndex > 5 -> "High"
        uvIndex < 5 -> "Low"
        else -> "Medium"
    }
}
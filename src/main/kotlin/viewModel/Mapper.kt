package viewModel

import data.remote.dto.Forecastday
import data.remote.dto.Astro
import data.remote.dto.Hour
import data.remote.dto.Weather
import utils.convertDate
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
        sunSet = forecast.forecastday[0].astro?.sunset!!,
        sunRise = forecast.forecastday[0].astro?.sunrise!!,
        daysForecastUiState = forecast.forecastday.toUIState()
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
    println(this[0].day?.condition?.icon)
    return map {
        DayForecastUiState(
            day = convertEpochMillisecondsToDate(it.dateEpoch ?: 0),
            iconUrl = it.day?.condition?.icon ?: "",
            minTemperature = (it.day?.mintempC ?: 0.0).toString(),
            maxTemperature = (it.day?.maxtempC ?: 0.0).toString(),
            dateOfDay = getDayOfWeek(it.dateEpoch ?: 0)
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
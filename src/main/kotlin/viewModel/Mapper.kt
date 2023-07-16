package viewModel

import data.remote.dto.Hour
import data.remote.dto.Weather

fun Weather.toUIState(): HomeUIState {
    return HomeUIState(
        forecastHourly = forecast.forecastday[0].hour?.map { it.toUIState() } ?: emptyList(),
        windDegree = (current?.windDegree ?: 0).toFloat(),
        windKph = current?.windKph ?: 0.0
    )
}

fun Hour.toUIState() = ForecastHour(
    time = time.substring(time.indexOf(" ")),
    icon = condition?.icon ?: "",
    temp = tempC ?: 0.0
)
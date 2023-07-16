package viewModel

import data.remote.dto.Hour
import data.remote.dto.Weather

data class HomeUIState(
    val forecastHourly: List<ForecastHour> = emptyList()
)

data class ForecastHour(
    val time: String = "",
    val icon: String = "",
    val temp: Double = 0.0
)

//Mapper
fun Weather.toUIState(): HomeUIState {
    return HomeUIState(
        forecastHourly = forecast.forecastday[0].hour?.map { it.toUIState() } ?: emptyList()
    )
}

fun Hour.toUIState() = ForecastHour(
    time = time.substring(time.indexOf(" ")),
    icon = condition?.icon ?: "",
    temp = tempC ?: 0.0
)






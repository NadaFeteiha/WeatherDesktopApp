package viewModel

import data.remote.dto.Hour
import data.remote.dto.Weather
import utils.convertTimeToHourAMPM
import utils.getTimeNowHourOnlyAsInt


fun Weather.toUIState(): HomeUIState {
    return HomeUIState(
        forecastHourly = forecast.forecastday[0].hour?.subList(getTimeNowHourOnlyAsInt(), 24)
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
        convertTimeToHourAMPM(time)
    },
    icon = condition?.icon ?: "",
    temp = tempC ?: 0.0
)

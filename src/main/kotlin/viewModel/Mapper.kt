package viewModel

import data.remote.dto.Hour
import data.remote.dto.Weather
import utils.convertTimeToHourAMPM
import utils.getTimeNowHourOnlyAsInt
import java.text.SimpleDateFormat
import java.util.*

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
        feelDescription = current?.condition?.text ?: "",
        date = convertDate(location?.localtime ?: ""),
        temperature = current?.tempC.toString(),
        cityName = location?.name ?: "",
        countryName = location?.country ?: "",
        icon = current?.condition?.icon ?: "",
        uvValue = (current?.uv?.toInt() ?: 0) * 10
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


fun convertDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM, yyyy h:mm a", Locale.getDefault())

    val parsedDate = inputFormat.parse(inputDate)
    return outputFormat.format(parsedDate)
}
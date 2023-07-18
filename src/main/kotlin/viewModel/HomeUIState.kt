package viewModel

data class HomeUIState(
    val forecastHourly: List<ForecastHour> = emptyList(),
    val windKph: Double = 0.0,
    val windDegree: Float = 0f,

    val humidityDescription: String = "",
    val humidityValue: String = "",
    val visibilityAvg: String = "",
    val feelsLike: String = "",
    val feelDescription: String = "",
    val uvValue: Int = 0
)

data class ForecastHour(
    val time: String = "",
    val icon: String = "",
    val temp: Double = 0.0
)








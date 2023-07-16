package viewModel

data class HomeUIState(
    val forecastHourly: List<ForecastHour> = emptyList(),
    val windKph: Double = 0.0,
    val windDegree: Float = 0f
)

data class ForecastHour(
    val time: String = "",
    val icon: String = "",
    val temp: Double = 0.0
)








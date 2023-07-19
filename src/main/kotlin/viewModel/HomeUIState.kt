package viewModel

data class HomeUIState(
    val isLoading:Boolean = false,
    val error:String= "",
    val forecastHourly: List<ForecastHour> = emptyList(),
    val windKph: Double = 0.0,
    val windDegree: Float = 0f,
    val humidityDescription: String = "",
    val humidityValue: String = "",
    val visibilityAvg: String = "",
    val feelsLike: String = "",
    val feelDescription: String = "",
    val date: String = "hh",
    val temperature: String = "",
    val cityName: String = "",
    val countryName: String = "",
    val icon: String = "",
    val uvValue: Int = 0,
    val suggestion: List<String> = emptyList(),
    val keyword: String = "",
    val daysForecastUiState: List<DayForecastUiState> = emptyList(),
    val isExpandMenuSuggestion: Boolean = false,
    val sunSet:String="",
    val sunRise:String="",
)

data class ForecastHour(
    val time: String = "",
    val icon: String = "",
    val temp: Double = 0.0
)

data class DayForecastUiState(
    val minTemperature: String = "",
    val maxTemperature: String = "",
    val dateOfDay: String = "",
    val day: String = "",
    val iconUrl: String = ""
)
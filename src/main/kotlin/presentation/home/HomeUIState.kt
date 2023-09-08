package presentation.home

import presentation.ErrorState

data class HomeUIState(
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
    val weatherUIState: WeatherUIState = WeatherUIState(),
    val keyword: String = "",
    val isExpandMenuSuggestion: Boolean = false,
    val isSearchExpanded: Boolean = false,
    val suggestion: List<String> = emptyList()
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

data class WeatherUIState(
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
    val uvIndexDescription: String = "",
    val daysForecastUiState: List<DayForecastUiState> = emptyList(),
    val sunSet: String = "00:00 PM",
    val sunRise: String = "00:00 AM",
    val sunriseTime: String = "",
    val sunsetTime: String = "",
    val currentTime: Long = 0L
)
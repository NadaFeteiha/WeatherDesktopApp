package data

import data.dto.LocationFromIP
import data.dto.SearchItem
import data.dto.Weather

interface WeatherService {

    suspend fun getWeatherByCityName(city: String, numDays: Int = 10): Weather

    suspend fun searchWeatherByCityName(city: String): List<SearchItem>

    suspend fun getLocation(): LocationFromIP
}
package data.remote

import data.remote.dto.LocationFromIP
import data.remote.dto.SearchItem
import data.remote.dto.Weather

interface WeatherService {

    suspend fun getWeatherByCityName(city: String, numDays: Int = 10): Weather

    suspend fun searchWeatherByCityName(city: String): List<SearchItem>

    suspend fun getLocation(): LocationFromIP
}
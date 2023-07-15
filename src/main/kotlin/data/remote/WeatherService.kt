package data.remote

import data.remote.dto.SearchItem
import data.remote.dto.Weather

interface WeatherService {

    suspend fun getWeatherByCityName(city: String): Weather

    suspend fun searchWeatherByCityName(city: String): List<SearchItem>

}
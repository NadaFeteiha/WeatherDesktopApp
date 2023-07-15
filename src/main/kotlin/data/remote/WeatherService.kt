package data.remote

import data.remote.dto.LocationFromIP
import data.remote.dto.Weather

interface WeatherService {

    suspend fun getWeatherByCityName(city: String): Weather

    suspend fun getLocation(): LocationFromIP
}
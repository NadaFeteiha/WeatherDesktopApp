package data.remote

import data.remote.dto.Weather

interface WeatherService {

    suspend fun getWeatherByCityName(city: String, numDays: Int = 8): Weather

}
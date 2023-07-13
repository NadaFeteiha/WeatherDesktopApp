package data.remote

import data.remote.dto.Weather
import io.ktor.client.call.*
import io.ktor.client.request.*

class WeatherServiceImpl : WeatherService {

    override suspend fun getWeatherByCityName(city: String, numDays: Int): Weather =
        WeatherServiceClient.client.get("forecast.json?q=${city}&days=$numDays").body()

}



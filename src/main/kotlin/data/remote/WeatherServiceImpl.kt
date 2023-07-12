package data.remote

import data.remote.dto.Weather
import io.ktor.client.call.*
import io.ktor.client.request.*

class WeatherServiceImpl : WeatherService {

    override suspend fun getWeatherByCityName(city: String): Weather =
        WeatherServiceClient.client.get("current.json?q=${city}&aqi=no").body()

}



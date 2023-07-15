package data.remote

import data.remote.dto.SearchItem
import data.remote.dto.Weather
import io.ktor.client.call.*
import io.ktor.client.request.*

class WeatherServiceImpl(private val weatherServiceClient : WeatherServiceClient) : WeatherService {

    override suspend fun getWeatherByCityName(city: String): Weather =
        weatherServiceClient.client.get("forecast.json?q=${city}&aqi=no").body()

    override suspend fun searchWeatherByCityName(city: String): List<SearchItem> {
        return  weatherServiceClient.client.get("search.json?q=${city}").body()
    }

}



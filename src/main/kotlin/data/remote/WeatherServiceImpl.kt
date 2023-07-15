package data.remote

import data.remote.dto.APIS
import data.remote.dto.LocationFromIP
import data.remote.dto.Weather
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.*

class WeatherServiceImpl : WeatherService {

    override suspend fun getWeatherByCityName(city: String): Weather =
        tryToExecute<Weather>(APIS.WEATHER_API, "forecast.json?q=${city}&aqi=no") { get(it) }


    override suspend fun getLocation(): LocationFromIP =
        tryToExecute<LocationFromIP>(APIS.LOCATION_API, "check") { get(it) }


    private suspend inline fun <reified T> tryToExecute(
        api: APIS,
        urlString: String,
        method: HttpClient.(urlString: String) -> HttpResponse
    ): T {
        try {
            WeatherServiceClient.clientAttributes.put(AttributeKey("API"), api.value)
            val response = WeatherServiceClient.client.method(urlString)
            return response.body<T>()
        } catch (e: Exception) {
            throw e
        }
    }

}

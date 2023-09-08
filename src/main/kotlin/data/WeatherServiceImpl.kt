package data

import data.dto.APIS
import data.dto.LocationFromIP
import data.dto.SearchItem
import data.dto.Weather
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.*

class WeatherServiceImpl(private val client: HttpClient, private val attributes: Attributes) : WeatherService {

    override suspend fun getWeatherByCityName(city: String, numDays: Int): Weather =
        tryToExecute<Weather>(APIS.WEATHER_API) {
            get("forecast.json") {
                parameter("q", city)
                parameter("days", numDays)
            }
        }

    override suspend fun getLocation(): LocationFromIP =
        tryToExecute<LocationFromIP>(APIS.LOCATION_API) { get("check") }

    override suspend fun searchWeatherByCityName(city: String): List<SearchItem> =
        tryToExecute<List<SearchItem>>(APIS.WEATHER_API) { get("search.json") { parameter("q", city) } }

    private suspend inline fun <reified T> tryToExecute(
        api: APIS,
        method: HttpClient.() -> HttpResponse
    ): T {
        try {
            attributes.put(AttributeKey("API"), api.value)
            val response = client.method()
            return response.body<T>()
        } catch (e: Throwable) {
            throw e
        }
    }
}


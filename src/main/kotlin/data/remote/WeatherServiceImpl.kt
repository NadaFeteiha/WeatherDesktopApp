package data.remote

import data.remote.dto.APIS
import data.remote.dto.LocationFromIP
import data.remote.dto.SearchItem
import data.remote.dto.Weather
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.*
import org.koin.core.component.KoinComponent

class WeatherServiceImpl(private val client: HttpClient) : WeatherService, KoinComponent {

    override suspend fun getWeatherByCityName(city: String, numDays: Int): Weather =
        tryToExecute<Weather>(APIS.WEATHER_API, "forecast.json?q=${city}&days=$numDays") { get(it) }

    override suspend fun getLocation(): LocationFromIP =
        tryToExecute<LocationFromIP>(APIS.LOCATION_API, "check") { get(it) }

    override suspend fun searchWeatherByCityName(city: String): List<SearchItem> =
        tryToExecute<List<SearchItem>>(APIS.WEATHER_API, "search.json?q=${city}") { get(it) }

    private suspend inline fun <reified T> tryToExecute(
        api: APIS,
        urlString: String,
        method: HttpClient.(urlString: String) -> HttpResponse
    ): T {
        try {
            WeatherServiceClient.clientAttributes.put(AttributeKey("API"), api.value)
            val response = WeatherServiceClient.client.method(urlString)
            return response.body<T>()
        } catch (e: Throwable) {
            throw e
        }
    }
}


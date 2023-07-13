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

    override suspend fun getWeatherByCityName(city: String): Weather {
        return fetchFromAPI<Weather>(APIS.WEATHER_API, "forecast.json?q=${city}&aqi=no") {
            get(it)
        }
        /*WeatherServiceClient.clientAttributes.put(AttributeKey("API"), APIS.WEATHER_API.value)
        return WeatherServiceClient.client.get("forecast.json?q=${city}&aqi=no").body()*/
    }

    override suspend fun getLocation(): LocationFromIP {
        return fetchFromAPI<LocationFromIP>(APIS.LOCATION_API, "check") {
            get(it)
        }
        /*WeatherServiceClient.clientAttributes.put(AttributeKey("API"), APIS.LOCATION_API.value)
        return WeatherServiceClient.client.get("check").body<String>()*/
    }

    private suspend inline fun <reified T> fetchFromAPI(
        api: APIS,
        urlString: String,
        method: HttpClient.(urlString: String) -> HttpResponse
    ): T {
        WeatherServiceClient.clientAttributes.put(AttributeKey("API"), api.value)
        val response = WeatherServiceClient.client.method(urlString)
        return response.body<T>()
    }

}
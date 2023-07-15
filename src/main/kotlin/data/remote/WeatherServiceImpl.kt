package data.remote

import data.remote.dto.Weather
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent

class WeatherServiceImpl(private val client: HttpClient) : WeatherService, KoinComponent {

    override suspend fun getWeatherByCityName(city: String, numDays: Int): Weather =
        client.get("forecast.json?q=${city}&days=$numDays").body()

}



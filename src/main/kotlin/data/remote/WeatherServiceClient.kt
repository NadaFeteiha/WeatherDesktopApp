package data.remote

import data.remote.dto.APIS
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*

object WeatherServiceClient {

    val clientAttributes = Attributes(true)

    val client = HttpClient(CIO) {
        expectSuccess = true
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        defaultRequest {
            when (clientAttributes[AttributeKey<String>("API")]) {
                APIS.WEATHER_API.value -> {
                    url("http://api.weatherapi.com/v1/")
                    header(HttpHeaders.ContentType, "application/json")
                    url.parameters.append("key", "3aed1336391c477ea48154514230907")
                }
                APIS.LOCATION_API.value -> {
                    url("http://apiip.net/api/")
                    url.parameters.append("accessKey", "633cc30c-cdd9-4a91-bd9c-a76885121aa5")
                }
            }
        }

        install(ContentNegotiation) {
            json()
        }

    }

}
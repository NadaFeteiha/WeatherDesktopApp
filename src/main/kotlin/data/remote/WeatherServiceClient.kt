package data.remote

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object WeatherServiceClient {

    val client = HttpClient(CIO) {
        expectSuccess = true
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        defaultRequest {
            url("http://api.weatherapi.com/v1/")
            header(HttpHeaders.ContentType, "application/json")
            url.parameters.append("key" , "3aed1336391c477ea48154514230907")
        }

        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

    }

}
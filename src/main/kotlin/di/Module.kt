package di

import controller.HomeController
import data.remote.WeatherService
import data.remote.WeatherServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val serviceModule = module {
    single {
        HttpClient(CIO) {
            expectSuccess = true

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                val timeout = 30000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
            }

            defaultRequest {
                url("http://api.weatherapi.com/v1/")
                header(HttpHeaders.ContentType, "application/json")
                url.parameters.append("key", "28e2ab3812ba4d068c2154452230907")
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
            }
        }
    }
}

val appModules = module {
    includes(
        serviceModule
    )
    single<WeatherService> { WeatherServiceImpl(get()) }
    singleOf(::HomeController) { bind<HomeController>() }

}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        appModules
    )
}
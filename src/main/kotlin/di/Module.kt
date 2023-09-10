package di

import data.WeatherService
import data.WeatherServiceImpl
import data.dto.APIS
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import me.nadafeteiha.WeatherApp.BuildConfig
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import presentation.home.HomeScreenModel


fun appModules() = module {
    includes(
        ServiceModule,
        NetworkModule,
        ScreenModelsModule
    )
}

val NetworkModule = module {
    single { Attributes(true) }

    single {

        val attributes = get<Attributes>()

        HttpClient(CIO) {
            expectSuccess = true
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            defaultRequest {
                when (attributes[AttributeKey<String>("API")]) {
                    APIS.WEATHER_API.value -> {
                        url("http://api.weatherapi.com/v1/")
                        header(HttpHeaders.ContentType, "application/json")
                        url.parameters.append("key", BuildConfig.WEATHER_API_KEY)
                    }

                    APIS.LOCATION_API.value -> {
                        url("http://apiip.net/api/")
                        url.parameters.append("accessKey", BuildConfig.LOCATION_API_KEY)
                    }
                }
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
}

val ServiceModule = module {
    singleOf(::WeatherServiceImpl) { bind<WeatherService>() }
}

val ScreenModelsModule = module {
    factoryOf(::HomeScreenModel)
}

package di

import controller.HomeController
import data.remote.WeatherService
import data.remote.WeatherServiceClient
import data.remote.WeatherServiceImpl
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val serviceModule = module {
    single { WeatherServiceClient.client }
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
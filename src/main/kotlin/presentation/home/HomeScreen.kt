package presentation.home

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.launch
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatform
import presentation.composables.*

object HomeScreen : Screen {

    @Composable
    override fun Content() {

        val viewModel = getScreenModel<HomeScreenModel>()

        val state by viewModel.state.collectAsState()
        val windowState = rememberWindowState()

        HomeScreenContent(
            windowState = windowState,
            listener = viewModel,
            state = state
        )
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun HomeScreenContent(
        modifier: Modifier = Modifier,
        windowState: WindowState,
        state: HomeUIState,
        listener: HomeInteractionListener,
    ) {
        val sunRiseTime = timeToMilliseconds(state.weatherUIState.sunRise)
        val sunSetTime = timeToMilliseconds(state.weatherUIState.sunSet)

        FlowRow(
            modifier = modifier
                .fillMaxSize()
                .width(windowState.size.width)
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.isLoading) {
                        LoadingAnimation()
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Error Internet Connection", style = MaterialTheme.typography.h1)
                            Button(
                                onClick = listener::getData,
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.secondary
                                )
                            ) {
                                Text("Try Again")
                            }
                        }
                    }
                }
            }

            BlurredCard(modifier = Modifier.padding(bottom = 16.dp), blurBackground = {
                WeatherImageLoader(
                    url = state.weatherUIState.icon,
                    modifier = Modifier.size(300.dp).blur(70.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                        .alpha(0.5f),
                )
            }) {
                SearchCard(
                    modifier = Modifier.width(374.dp).height(378.dp),
                    date = state.weatherUIState.date,
                    cityName = state.weatherUIState.cityName,
                    countryName = state.weatherUIState.countryName,
                    temperature = state.weatherUIState.temperature,
                    icon = state.weatherUIState.icon,
                    keyword = state.keyword,
                    suggestion = state.suggestion,
                    isExpandMenuSuggestion = state.isExpandMenuSuggestion,
                    isSearchExpanded = state.isSearchExpanded,
                    listener = listener
                )
            }

            BlurredCard(modifier = Modifier.padding(bottom = 16.dp)) {
                HourlyForecast(
                    modifier = Modifier.height(378.dp).width(782.dp).padding(horizontal = 24.dp, vertical = 16.dp),
                    forecastHourly = state.weatherUIState.forecastHourly,
                    humidityDescription = state.weatherUIState.humidityDescription,
                    humidityValue = state.weatherUIState.humidityValue,
                    visibilityAvg = state.weatherUIState.visibilityAvg,
                    feelsLike = state.weatherUIState.feelsLike,
                    feelDescription = state.weatherUIState.feelDescription
                )
            }

            BlurredCard(
                blurBackground = {
                    if (state.weatherUIState.daysForecastUiState.isNotEmpty()) {
                        WeatherImageLoader(
                            url = state.weatherUIState.daysForecastUiState[0].iconUrl,
                            modifier = Modifier.size(300.dp).blur(80.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                                .alpha(0.5f),
                        )
                    }
                }
            ) {
                Column(
                    modifier = Modifier.height(344.dp).width(374.dp)
                ) {
                    Text(
                        text = "10 Day Forecast",
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(16.dp)
                    )

                    val scrollState = rememberLazyListState()
                    val coroutineScope = rememberCoroutineScope()
                    LazyColumn(
                        state = scrollState,
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier
                            .widthIn(390.dp)
                            .draggable(
                                orientation = Orientation.Vertical,
                                state = rememberDraggableState { delta ->
                                    coroutineScope.launch {
                                        scrollState.scrollBy(-delta)
                                    }
                                }),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    ) {
                        items(state.weatherUIState.daysForecastUiState) { dayForecastUiState ->
                            DayForecast(state = dayForecastUiState)
                        }
                    }
                }
            }


            BlurredCard {
                Column(
                    modifier = Modifier.width(250.dp).height(344.dp).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        "Wind Status",
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Compass(windKph = state.weatherUIState.windKph, windDegree = state.weatherUIState.windDegree)
                }
            }

            BlurredCard {
                ProgressBar(
                    modifier = Modifier.width(250.dp).height(344.dp),
                    indicatorValue = state.weatherUIState.uvValue,
                    uvDescription = state.weatherUIState.uvIndexDescription
                )
            }

            BlurredCard {
                Column(
                    modifier = Modifier.width(250.dp).height(344.dp),
                ) {
                    Text(
                        "Sunrise & Sunset",
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(top = 16.dp, start = 24.dp)
                    )

                    SunriseSunsetView(
                        sunriseTimeLong = sunRiseTime,
                        sunsetTimeLong = sunSetTime,
                        currentTime = state.weatherUIState.currentTime,
                        sunsetTime = state.weatherUIState.sunsetTime,
                        sunriseTime = state.weatherUIState.sunriseTime
                    )
                }
            }
        }
    }

    @Composable
    inline fun <reified T : ScreenModel> getScreenModel(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null,
    ): T {
        val koin = KoinPlatform.getKoin()
        return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
    }
}

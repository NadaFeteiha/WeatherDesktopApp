package ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import com.seiko.imageloader.asImageBitmap
import kotlinx.coroutines.launch
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Codec
import org.jetbrains.skia.Data
import ui.composables.*
import ui.theme.grey
import utils.timeToMilliseconds
import viewModel.HomeInteractionListener
import viewModel.HomeUIState
import java.net.URL


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    windowState: WindowState,
    state: HomeUIState,
    listener: HomeInteractionListener,
) {
    val sunRiseTime = timeToMilliseconds(state.sunRise)
    val sunSetTime = timeToMilliseconds(state.sunSet)

    FlowRow(
        modifier = modifier
            .fillMaxSize()
            .width(windowState.size.width)
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (state.isLoading || state.error.isNotEmpty()) {
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
                url = state.icon,
                modifier = Modifier.size(300.dp).blur(70.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                    .alpha(0.5f),
            )
        }) {
            SearchCard(
                modifier = Modifier.width(380.dp),
                date = state.date,
                cityName = state.cityName,
                countryName = state.countryName,
                temperature = state.temperature,
                icon = state.icon,
                keyword = state.keyword,
                suggestion = state.suggestion,
                isExpandMenuSuggestion = state.isExpandMenuSuggestion,
                isSearchExpanded = state.isSearchExpanded,
                listener = listener
            )
        }

        BlurredCard(modifier = Modifier.padding(bottom = 16.dp)) {
            HourlyForecast(
                modifier = Modifier.padding(24.dp),
                forecastHourly = state.forecastHourly,
                humidityDescription = state.humidityDescription,
                humidityValue = state.humidityValue,
                visibilityAvg = state.visibilityAvg,
                feelsLike = state.feelsLike,
                feelDescription = state.feelDescription
            )
        }
        BlurredCard(
            blurBackground = {
                if (state.daysForecastUiState.isNotEmpty()) {
                    WeatherImageLoader(
                        url = state.daysForecastUiState[0].iconUrl,
                        modifier = Modifier.size(300.dp).blur(80.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                            .alpha(0.5f),
                    )
                }
            }
        ) {
            val scrollState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()
            LazyColumn(
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .height(300.dp)
                    .widthIn(390.dp)
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            coroutineScope.launch {
                                scrollState.scrollBy(-delta)
                            }
                        }),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
            ) {
                items(state.daysForecastUiState) { dayForecastUiState ->
                    DayForecast(state = dayForecastUiState)
                }
            }
        }

        BlurredCard {
            Column(
                modifier = Modifier.size(300.dp).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text("Wind Status", style = MaterialTheme.typography.h2)

                Compass(windKph = state.windKph, windDegree = state.windDegree)
            }
        }
        BlurredCard {
            Column {
                Text("SunRise&SunSet",
                    style = MaterialTheme.typography.h2,
                    modifier=Modifier.padding(top = 16.dp, start = 24.dp)
                )
                SunriseSunsetView(
                    sunriseTimeLong = sunRiseTime,
                    sunsetTimeLong = sunSetTime,
                )
            }
        }

        BlurredCard {
            ProgressBar(
                modifier = Modifier.size(300.dp).background(
                    color = grey.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(24.dp),
                ),
                indicatorValue = state.uvValue,
                uvDescription = state.uvIndexDescription
            )
        }

    }

}

@Composable
fun LoadingAnimation(
    size: Dp = 64.dp,
    sweepAngle: Float = 90f,
    color: Color = MaterialTheme.colors.secondary,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth
) {
    val transition = rememberInfiniteTransition()

    val currentArcStartAngle by transition.animateValue(
        0,
        360,
        Int.VectorConverter,
        infiniteRepeatable(
            animation = tween(
                durationMillis = 1100,
                easing = LinearEasing
            )
        )
    )


    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Square)
    }

    // draw on canvas
    Canvas(
        Modifier
            .progressSemantics() // (optional) for Accessibility services
            .size(size) // canvas size
            .padding(strokeWidth / 2) //padding. otherwise, not the whole circle will fit in the canvas
    ) {
        drawCircle(Color.LightGray, style = stroke)

        // draw arc with the same stroke
        drawArc(
            color,
            startAngle = currentArcStartAngle.toFloat() - 90,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = stroke
        )
    }
}

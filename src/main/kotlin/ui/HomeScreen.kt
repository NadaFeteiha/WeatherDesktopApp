package ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import com.seiko.imageloader.asImageBitmap
import kotlinx.coroutines.launch
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Codec
import ui.composables.*
import ui.theme.grey
import viewModel.HomeInteractionListener
import viewModel.HomeUIState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    windowState: WindowState,
    state: HomeUIState,
    listener: HomeInteractionListener,
) {
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

                Text("Loading.....")
//                val codec = remember {
//                    val bytes =
//                        URL("https://media.emailonacid.com/wp-content/uploads/2019/03/2019-GifsInEmail.gif").readBytes()
//                    Codec.makeFromData(Data.makeFromBytes(bytes))
//                }
//                GifAnimation(codec, Modifier.size(100.dp))

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
                    .widthIn( 390.dp)
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
            ProgressBar(
                modifier = Modifier.size(300.dp).background(
                    color = grey.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(24.dp),
                ),
                indicatorValue = state.uvValue
            )
        }

    }

}

@Composable
fun GifAnimation(codec: Codec, modifier: Modifier) {
    val transition = rememberInfiniteTransition()
    val frameIndex by transition.animateValue(
        initialValue = 0,
        targetValue = codec.frameCount - 1,
        Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 10
                for ((index, frame) in codec.framesInfo.withIndex()) {
                    index at durationMillis
                    durationMillis += frame.duration
                }
            }
        )
    )

    val bitmap = remember { Bitmap().apply { allocPixels(codec.imageInfo) } }
    Canvas(modifier) {
        codec.readPixels(bitmap, frameIndex)
        drawImage(bitmap.asImageBitmap())
    }
}

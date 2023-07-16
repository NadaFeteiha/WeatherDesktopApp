package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import composables.BlurredCard
import composables.Compass
import composables.HourlyForecastItem
import kotlinx.coroutines.launch
import viewModel.ForecastHour
import viewModel.HomeInteractionListener
import viewModel.HomeUIState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    windowState: WindowState,
    state: HomeUIState,
    listener: HomeInteractionListener
) {
    Column(modifier = modifier.fillMaxSize().width(windowState.size.width)) {

        BlurredCard {
            HourlyForecast(
                modifier = Modifier.width(windowState.size.width),
                forecastHourly = state.forecastHourly,
            )
        }

        Compass(windKph = state.windKph, windDegree = state.windDegree)
    }
}


@Composable
fun HourlyForecast(
    modifier: Modifier = Modifier,
    forecastHourly: List<ForecastHour>
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth().padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Hourly Forecast",
            style = MaterialTheme.typography.h1
        )

        LazyRow(
            state = scrollState,
            modifier = Modifier.fillMaxWidth().draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(forecastHourly) { hourly ->
                HourlyForecastItem(
                    time = "5PM",//hourly.time,
                    temperature = hourly.temp,
                    icon = hourly.icon
                )
            }
        }
    }
}
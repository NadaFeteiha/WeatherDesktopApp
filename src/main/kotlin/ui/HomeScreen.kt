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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import composables.*
import kotlinx.coroutines.launch
import ui.theme.grey
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
    Column(modifier = modifier.fillMaxSize().width(windowState.size.width).padding(24.dp)) {

        BlurredCard {
            HourlyForecast(
                modifier = Modifier.background(
                    color = grey.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(24.dp)
                ).width(windowState.size.width),
                forecastHourly = state.forecastHourly,
                humidityDescription = state.humidityDescription,
                humidityValue = state.humidityValue,
                visibilityAvg = state.visibilityAvg,
                feelsLike = state.feelsLike,
                feelDescription = state.feelDescription
            )
        }

        Spacer(Modifier.height(24.dp))

        BlurredCard {
            Column(
                modifier = Modifier.background(
                    color = grey.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(24.dp)
                ).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text("Wind Status", style = MaterialTheme.typography.h2)

                Compass(windKph = state.windKph, windDegree = state.windDegree)
            }
        }
    }
}


package composables

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
import kotlinx.coroutines.launch
import ui.theme.grey
import viewModel.ForecastHour

@Composable
fun HourlyForecast(
    modifier: Modifier = Modifier,
    forecastHourly: List<ForecastHour>,
    humidityDescription: String,
    humidityValue: String,
    visibilityAvg: String,
    feelsLike: String,
    feelDescription: String
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth().padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            modifier = Modifier.padding(start = 24.dp),
            text = "Hourly Forecast",
            style = MaterialTheme.typography.h1
        )

        BlurredCard(modifier = Modifier.padding(horizontal = 24.dp)) {
            LazyRow(
                state = scrollState,
                modifier = Modifier.background(
                    color = grey.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(24.dp)
                ).fillMaxWidth()
                    .padding(vertical = 20.dp).draggable(
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
                        time = hourly.time,
                        temperature = hourly.temp,
                        icon = hourly.icon
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            HourlyItem(
                icon = "humidity.svg",
                title = "Humidity",
                weatherType = humidityDescription,
                value = humidityValue
            )

            HourlyItem(
                icon = "visibility.svg",
                title = "Visibility",
                weatherType = "Average",
                value = visibilityAvg
            )


            HourlyItem(
                icon = "temperature.svg",
                title = "Feels Like",
                weatherType = "it's $feelDescription now",
                value = "$feelsLike\u00B0",
                isSmallText = true
            )

        }
    }
}
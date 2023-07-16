package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowState
import composable.HourlyForecastItem
import composable.ProgressBar
import ui.theme.Black
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
        Button(onClick = listener::getData) {
            Text("Get Data")
        }

        Text("Width = ${windowState.size.width}")


        HourlyForecast(
            forecastHourly = state.forecastHourly,
            modifier = Modifier.width(windowState.size.width)
        )
    }
}


@Composable
fun HourlyForecast(
    modifier: Modifier = Modifier,
    forecastHourly: List<ForecastHour>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Hourly Forecast",
            style = MaterialTheme.typography.h1
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
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
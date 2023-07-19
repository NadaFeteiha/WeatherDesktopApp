package ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ui.composables.WeatherImageLoader
import ui.theme.maxTemperatureSpanStyle
import ui.theme.minTemperatureSpanStyle
import viewModel.DayForecastUiState

@Composable
fun DayForecast(
    state: DayForecastUiState,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        WeatherImageLoader(
            modifier = Modifier.padding(end = 18.dp).size(28.dp),
            url = state.iconUrl
        )

        Text(
            text = buildAnnotatedString {
                withStyle(maxTemperatureSpanStyle) { append(state.minTemperature + "°") }
                withStyle(minTemperatureSpanStyle) { append("/" + state.minTemperature + "°") }
            },
            modifier = Modifier.width(128.dp),
        )

        Text(
            text = state.day,
            style = MaterialTheme.typography.body2.copy(),
            modifier = Modifier.padding(end = 48.dp).width(48.dp),
        )

        Text(
            text = state.dateOfDay,
            style = MaterialTheme.typography.body1,
        )

    }
}
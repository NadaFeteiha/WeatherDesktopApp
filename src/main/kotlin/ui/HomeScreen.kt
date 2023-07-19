package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
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

        BlurredCard(modifier = Modifier.padding(bottom = 16.dp)) {
            SearchCard(
                modifier = Modifier.background(
                    color = grey.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(24.dp)
                ),
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
                modifier = Modifier.width(700.dp).background(
                    color = grey.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(24.dp)
                ),
                forecastHourly = state.forecastHourly,
                humidityDescription = state.humidityDescription,
                humidityValue = state.humidityValue,
                visibilityAvg = state.visibilityAvg,
                feelsLike = state.feelsLike,
                feelDescription = state.feelDescription
            )
        }

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

        BlurredCard {
            ProgressBar(
                modifier = Modifier.size(250.dp).background(
                    color = grey.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(24.dp),
                ),
                indicatorValue = state.uvValue
            )
        }

    }
}

package ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import composables.*
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

        SearchCard(
            date = state.date,
            cityName = state.cityName,
            countryName = state.countryName,
            temperature = state.temperature,
            icon = state.icon
        )

//        BlurredCard {
//            HourlyForecast(
//                modifier = Modifier.background(
//                    color = grey.copy(alpha = 0.4f),
//                    shape = RoundedCornerShape(24.dp)
//                ).width(windowState.size.width),
//                forecastHourly = state.forecastHourly,
//                humidityDescription = state.humidityDescription,
//                humidityValue = state.humidityValue,
//                visibilityAvg = state.visibilityAvg,
//                feelsLike = state.feelsLike,
//                feelDescription = state.feelDescription
//            )
//        }
//
//        Spacer(Modifier.height(24.dp))
//
//        BlurredCard {
//            Column(
//                modifier = Modifier.background(
//                    color = grey.copy(alpha = 0.4f),
//                    shape = RoundedCornerShape(24.dp)
//                ).padding(16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp),
//                horizontalAlignment = Alignment.Start
//            ) {
//                Text("Wind Status", style = MaterialTheme.typography.h2)
//
//                Compass(windKph = state.windKph, windDegree = state.windDegree)
//            }
//        }
    }
}



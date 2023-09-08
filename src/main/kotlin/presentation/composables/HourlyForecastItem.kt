package presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HourlyForecastItem(
    modifier: Modifier = Modifier,
    time: String,
    temperature: Double,
    icon: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(time, style = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = Bold))

        WeatherImageLoader(modifier = Modifier.size(24.dp), url = icon)

        Text("$temperature \u00B0", style = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = Bold))
    }
}






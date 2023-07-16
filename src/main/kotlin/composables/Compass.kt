package composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Compass(
    state: CompassUiState
) {
    Box(modifier = Modifier.size(192.dp), contentAlignment = Alignment.Center) {
        Icon(
            painter = painterResource("arrow.svg"),
            contentDescription = "JetBrains",
            modifier = Modifier
                .size(192.dp)
                .rotate(-state.windDegree)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = state.windKph.toString(), style = TextStyle(fontSize = 32.sp))
            Text(text = "Km/h", style = TextStyle(fontSize = 20.sp))
        }
        Icon(
            painter = painterResource("compass.svg"),
            contentDescription = "JetBrains",
            modifier = Modifier
                .size(192.dp)
        )
    }
}

data class CompassUiState(
    val windKph: Float = 0f,
    val windDegree: Float = 0f
)
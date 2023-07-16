package composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun Compass(
    state: CompassUiState
){
    Box(modifier = Modifier.size(192.dp), contentAlignment = Alignment.Center) {
        Icon(
            painter = painterResource("arrow.svg"),
            contentDescription = "JetBrains",
            modifier = Modifier
                .size(192.dp)
                .rotate(-state.windDegree)
        )
        Icon(
            painter = painterResource("compass.svg"),
            contentDescription = "JetBrains",
            modifier = Modifier
                .size(192.dp)
        )
    }
}

data class CompassUiState(
    val windKph : Float = 0f,
    val windDegree : Float = 0f
)
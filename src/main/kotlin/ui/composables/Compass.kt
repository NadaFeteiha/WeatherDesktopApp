package ui.composables

import androidx.compose.foundation.layout.*
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
    modifier: Modifier = Modifier,
    windKph: Double,
    windDegree: Float
) {
    Box(
        modifier = modifier.fillMaxWidth().aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource("arrow.svg"),
            contentDescription = "JetBrains",
            modifier = Modifier
                .size(180.dp)
                .rotate(-windDegree)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = windKph.toString(), style = TextStyle(fontSize = 32.sp))
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
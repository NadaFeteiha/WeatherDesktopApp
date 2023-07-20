package ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import java.util.*
import kotlin.math.*


@Composable
fun SunriseSunsetView(
    arcRadius: Dp = 150.dp,
    strokeWidth: Dp = 3.dp,
    sunriseTextColor: Color = Color(0xFF737679),
    sunsetTextColor: Color = Color(0xFF737679),
    sunriseTimeLong: Long,
    sunsetTimeLong: Long,
    sunriseTime: String,
    sunsetTime: String,
    currentTime: Long,
    arcColorArray: Array<Pair<Float, Color>> = arrayOf(
        0.1f to Color(0xccECD179),
        0.2f to Color(0xccF1DFA5)
    )
) {
    val bitmap = useResource("sun_icon_re.png") { loadImageBitmap(it) }

    val timeDifference = sunsetTimeLong.minus(sunriseTimeLong)
    val percentage =
        (currentTime.toFloat().minus(sunriseTimeLong.toFloat())).div(timeDifference.toFloat())

    var animationPlayed by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(arcRadius * 1.8f, arcRadius * 1.7f)
    ) {

        Canvas(modifier = Modifier.size(arcRadius * 1.3f)) {
            drawArc(
                brush = Brush.radialGradient(
                    colorStops = arcColorArray,
                    tileMode = TileMode.Clamp,
                ),
                startAngle = 180f,
                sweepAngle = 182f,
                style = Stroke(
                    strokeWidth.toPx(),
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 8f), 0f)
                ),
                useCenter = false,
            )

            val angleInDegrees = if (((percentage * 180.0) + 90) > 270) {
                270.0
            } else if ((((percentage * 180.0) + 90) < 90)) {
                90.0
            } else {
                (percentage * 180.0) + 90
            }
            val rad = (size.height / 2)
            val x =
                -(rad * sin(Math.toRadians(angleInDegrees))).toFloat() + (size.width / 2)
            val y =
                (rad * cos(Math.toRadians(angleInDegrees))).toFloat() + (size.height / 2)

            drawImage(
                image = bitmap,
                topLeft = Offset(x - 20f, y - 20f), // Set the desired offsets here
            )
        }

        Box(modifier = Modifier.padding(top = 24.dp)) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 21.dp, end = 21.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource("rectangle_small.png"),
                        contentDescription = "",
                        modifier = Modifier.size(18.dp, 10.dp)

                    )
                    Image(
                        painter = painterResource("rectangle_small.png"),
                        contentDescription = "",
                        modifier = Modifier.size(18.dp, 10.dp)
                    )
                }
                Image(
                    painter = painterResource("rectangle_tall.png"),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentWidth()
                .offset(y = 40.dp, x = (-90).dp)
                .padding(top = 12.dp)
        ) {
            Text(
                text = "Sunrise",
                color = sunriseTextColor,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Text(
                text = sunriseTime,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.body2
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentWidth()
                .offset(y = 40.dp, x = 90.dp)
                .padding(top = 12.dp)
        ) {
            Text(
                text = "Sunset",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = sunsetTextColor,
            )
            Text(
                text = sunsetTime,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.White,
                style = MaterialTheme.typography.body2
            )
        }
    }
}
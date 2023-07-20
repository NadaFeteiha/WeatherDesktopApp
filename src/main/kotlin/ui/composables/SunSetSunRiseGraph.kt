package ui.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import io.ktor.http.ContentDisposition.Parameters.Size
import utils.Util.getFormattedDateFromUnixTime
import java.util.*
import javax.swing.text.StyleConstants.Size
import kotlin.math.*


@Composable
fun SunriseSunsetView(
    arcRadius: Dp = 150.dp,
    strokeWidth: Dp = 3.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0,
    sunRadius: Float = 50f,
    sunriseTextString: String = "Sunrise",
    sunriseTextColor: Color = Color(0xFF737679),
    sunsetTextString: String = "Sunset",
    sunsetTextColor: Color = Color(0xFF737679),
    sunriseTimeLong: Long = Calendar.getInstance(Locale.getDefault()).apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MINUTE, 18)
        set(Calendar.HOUR_OF_DAY, 6)
    }.timeInMillis,

    sunsetTimeLong: Long = Calendar.getInstance(Locale.getDefault()).apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MINUTE, 18)
        set(Calendar.HOUR_OF_DAY, 18)
    }.timeInMillis,
    sunriseTimeColor: Color = Color.Black,
    sunsetTimeColor: Color = Color.Black,
    timeFormat: String = "HH:mm",
    arcColorArray: Array<Pair<Float, Color>> = arrayOf(
        0.1f to Color(0xccECD179),
        0.2f to Color(0xccF1DFA5)
    ),
    backGroundArray: Array<Pair<Float, Color>> = arrayOf(
        0.1f to Color(0xCCFFFFFF),
        0.2f to Color(0xFFF5D879),
    )

) {

    val bitmap = useResource("sun_icon_re.png") { loadImageBitmap(it) }


    val currentCalendar = Calendar.getInstance()
    val currentUnixTime = currentCalendar.timeInMillis

    val timeDifference = sunsetTimeLong.minus(sunriseTimeLong)
    val percentage =
        (currentUnixTime.toFloat().minus(sunriseTimeLong.toFloat())).div(timeDifference.toFloat())

    var animationPlayed by rememberSaveable {
        mutableStateOf(false)
    }
    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }



    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(arcRadius * 1.8f, arcRadius * 1.7f)
    ) {

        Canvas(
            modifier = Modifier
                .size(arcRadius * 1.3f)

        ) {


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
            fun rectangularToPolar(x: Float, y: Float): List<Float> {
                val r = sqrt(x.pow(2) + y.pow(2))
                val theta = (atan2(y, x))
                return listOf(r, theta)
            }
            val polarForm =rectangularToPolar(x,y)

            drawImage(
                image = bitmap,
                topLeft = Offset(x - 20f, y - 20f), // Set the desired offsets here
            )

//            drawArc(
//                color = Color.Cyan,
//                startAngle = 180f,
//                sweepAngle = -polarForm[1],
//                useCenter = true,
//                topLeft = Offset(x - (size.width/2), y - (size.height/2)),
//                size = Size(polarForm[0]*2,polarForm[0]*2)
//            )

            if (currentUnixTime<sunsetTimeLong){
            drawRoundRect(
                brush = Brush.horizontalGradient(
                    colorStops = backGroundArray,
                    tileMode = TileMode.Clamp,
                ),
                alpha = 0.05f,
                cornerRadius = CornerRadius(0f),
                topLeft = Offset.Zero,
                size = this.size.copy(x, 145f),
            )}
        }
        Box(modifier = Modifier.padding(top = 24.dp)) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp),
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
                text = sunriseTextString,
                color = sunriseTextColor,
                modifier = Modifier.align(Alignment.CenterHorizontally),

                )
            Text(
                text = sunriseTimeLong.getFormattedDateFromUnixTime(timeFormat),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.body1
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
                text = sunsetTextString,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = sunsetTextColor,
            )
            Text(
                text = sunsetTimeLong.getFormattedDateFromUnixTime(timeFormat),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.White,
                style = MaterialTheme.typography.body1

            )

        }
    }

}

@Preview
@Composable
fun GraphPreview() {
    SunriseSunsetView()
}
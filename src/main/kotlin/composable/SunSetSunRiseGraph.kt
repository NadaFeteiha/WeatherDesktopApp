package composable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import util.Util.getFormattedDateFromUnixTime
import java.util.*
import kotlin.math.cos
import kotlin.math.sin



@Composable
fun SunriseSunsetView(
    arcRadius: Dp = 150.dp,
    strokeWidth: Dp = 2.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0,
    sunRadius: Float = 50f,
    sunriseTextString: String = "Sunrise",
    sunriseTextColor: Color = Color(0xFF737679),
    sunsetTextString: String = "Sunset",
    sunsetTextColor: Color = Color(0xFF737679),
    sunriseTextTextStyle: TextStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),
    sunsetTextTextStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.End
    ),
    sunriseTimeLong: Long = Calendar.getInstance(Locale.getDefault()).apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MINUTE, 12)
        set(Calendar.HOUR_OF_DAY, 7)
    }.timeInMillis,

    sunsetTimeLong: Long = Calendar.getInstance(Locale.getDefault()).apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MINUTE, 33)
        set(Calendar.HOUR_OF_DAY, 17)
    }.timeInMillis,
    sunriseTimeColor: Color = Color.Black,

    sunsetTimeColor: Color = Color.Black,
    sunriseTimeTextStyle: TextStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold),
    sunsetTimeTextStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.End
    ),
    timeFormat: String = "HH:mm",
    arcColorArray: Array<Pair<Float, Color>> = arrayOf(
        0.2f to Color(0xFFBDBCBB),
        0.5f to Color(0xFFE8EBED)
    ),
    sunColorArray: Array<Pair<Float, Color>> = arrayOf(
        0.3f to Color(0xFFF74822),
        0.4f to Color(0xFFF5836A),
        0.9f to Color(0xFFFAF1D1),
        1f to Color(0xFFFFFBEE)
    )

) {

//    val density = LocalDensity.current
//    var ideaLogo = remember {
//        useResource("sun-icon.svg") { loadSvgPainter(it,density) }
//    }

    val currentCalendar = Calendar.getInstance(Locale.getDefault())
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
            .size(arcRadius * 2.5f)
    ) {

        Canvas(
            modifier = Modifier
                .size(arcRadius * 2)

        ) {

//            drawIntoCanvas {
//                it.withSave {
//                    with(ideaLogo) {
//                        draw(ideaLogo.intrinsicSize)
//                    }
//                    it.translate(ideaLogo.intrinsicSize.width, 0f)
//                    with(ideaLogo) {
//                        draw(Size(100f, 100f))
//                    }
//                }
//            }

            drawArc(
                brush = Brush.verticalGradient(
                    colorStops = arcColorArray,
                    tileMode = TileMode.Clamp
                ),
                startAngle = 180f,
                sweepAngle = 180f,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
                useCenter = false,
            )

            val angleInDegrees = if (((currentPercentage.value * 180.0) + 90) > 270) {
                270.0
            } else if ((((currentPercentage.value * 180.0) + 90) < 90)) {
                90.0
            } else {
                (currentPercentage.value * 180.0) + 90
            }
            val rad = (size.height / 2)
            val x =
                -(rad * sin(Math.toRadians(angleInDegrees))).toFloat() + (size.width / 2)
            val y =
                (rad * cos(Math.toRadians(angleInDegrees))).toFloat() + (size.height / 2)

            drawCircle(
                brush = Brush.radialGradient(
                    colorStops = sunColorArray,
                    center = Offset(x, y),
                    radius = sunRadius + 10
                ),
                radius = sunRadius,
                center = Offset(x, y)
            )

//
//            translate(left = x, top = y) {
//                with(ideaLogo) {
//                    draw(
//                        ideaLogo.intrinsicSize
//                    )
//                }
//            }

        }




        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentWidth()
                .offset(y = 30.dp, x = -arcRadius)


        ) {
            Text(
                text = sunriseTextString,
                style = sunriseTextTextStyle,
                color = sunriseTextColor,
                modifier = Modifier.align(Alignment.CenterHorizontally)


            )
            Text(
                text = sunriseTimeLong.getFormattedDateFromUnixTime(timeFormat),
                style = sunriseTimeTextStyle,
                color = sunriseTimeColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentWidth()
                .offset(y = 30.dp, x = arcRadius)


        ) {
            Text(
                text = sunsetTextString,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = sunsetTextTextStyle,
                color = sunsetTextColor,
            )
            Text(
                text = sunsetTimeLong.getFormattedDateFromUnixTime(timeFormat),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = sunsetTimeTextStyle,
                color = sunsetTimeColor,

                )

        }
    }

}
@Preview
@Composable
fun GraphPreview(){
    SunriseSunsetView()
}
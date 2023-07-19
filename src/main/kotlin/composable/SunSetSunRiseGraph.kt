package composable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import util.Util.getFormattedDateFromUnixTime
import java.util.*
import kotlin.math.cos
import kotlin.math.sin



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
        0.2f to Color(0xFFECD179),
        0.5f to Color(0xbbECD179)
    ),
    backGroundArray: Array<Pair<Float, Color>> = arrayOf(
        0.05f to Color(0xFFECD179),
        0.4f to Color(0xFFECD179),
        0.9f to Color(0xFFF5D879),
    )

) {

//    val image =painterResource("sun_icon.png")
//    val state = rememberUpdatedState(image)

//    val bitmap = useResource("sun_icon.png") { loadImageBitmap(it) }
//    val dogImage = BitmapPainter(
//        image= bitmap,
//        srcOffset= IntOffset(0,0),
//    srcSize= IntSize(50,50)
//    )
    var xOffset by remember {
        mutableStateOf(0f)
    }
    var yOffset by remember {
        mutableStateOf(0f)
    }
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
//                   with(image) {
//                        draw(image.intrinsicSize)
//                    }
//                    it.translate(image.intrinsicSize.width, 0f)
//                   with(image) {
//                       draw(Size(100f, 100f))
//                    }
//                }
//            }

            drawArc(
                brush = Brush.verticalGradient(
                    colorStops = arcColorArray,
                    tileMode = TileMode.Clamp,
                ),
                startAngle = 180f,
                sweepAngle = 182f,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round
                    , pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 10f), 0f)),
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


//            drawCircle(
//                brush = Brush.radialGradient(
//                    colorStops = sunColorArray,
//                    center = Offset(x, y),
//                    radius = sunRadius
//                ),
//                radius = sunRadius,
//                center = Offset(x, y)
//            )



            val outerRadius = size / 15F
            val innerRadius = size / 30F
            val angle = Math.PI / 5

            // Draw the star
            val path = Path()
            for (i in 0 until 5 * 4) {
                val radius = if (i % 2 == 0) outerRadius else innerRadius
                val x1 = x + radius.width * cos((angle * i).toFloat())
                val y1 = y + (radius.height * sin((angle * i).toFloat()))

                if (i == 0) {
                    path.moveTo(x1.toFloat(), y1.toFloat())
                } else {
                    path.lineTo(x1.toFloat(), y1.toFloat())
                }
            }
            path.close()
            drawPath(
                path = path,
                color = Color.Yellow,
                style = Fill // Use Fill style to fill the star
            )

            // Draw the circle in the center of the star
            drawCircle(
                color = Color(0xffFFC62D), // Use the same color as the star's fill color
                radius = 10.dp.toPx(),
                center = Offset(x, y)
            )
            drawRoundRect( brush = Brush.radialGradient(
                colorStops = backGroundArray,
                tileMode = TileMode.Clamp,
            ),
                alpha = 0.01f,
                cornerRadius = CornerRadius(0f),
                topLeft = Offset.Zero,
                size = this.size.copy(x,450f),)
        }
        Box(modifier =Modifier.padding(top=24.dp)){
            Column {
                Row (modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp), horizontalArrangement = Arrangement.SpaceBetween){
                    Image(painter = painterResource("rectangle_small.png"),
                        contentDescription ="",
                        modifier = Modifier.size(18.dp,10.dp)

                    )
                    Image(painter = painterResource("rectangle_small.png"),
                        contentDescription ="",
                        modifier = Modifier.size(18.dp,10.dp)
                    )
                }
                Image(painter = painterResource("rectangle_tall.png"),
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
                .offset(y = 30.dp, x = -arcRadius)
                .padding(top = 12.dp)


        ) {
            Text(
                text = sunriseTextString,
                color = sunriseTextColor,
                modifier = Modifier.align(Alignment.CenterHorizontally)


            )
            Text(
                text = sunriseTimeLong.getFormattedDateFromUnixTime(timeFormat),
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
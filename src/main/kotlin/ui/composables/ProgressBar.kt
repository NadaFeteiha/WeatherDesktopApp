package ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    backgroundIndicatorStrokeWidth: Float = 30f,
    foregroundIndicatorStrokeWidth: Float = 30f,
    indicatorValue: Int,
    uvDescription: String,
    maxIndicatorValue: Int = 100,
    backgroundIndicatorColor: Color = Color(0x1A7AD3FF),
    foregroundIndicatorColor: Color = MaterialTheme.colors.secondary,
) {
    val percentage = ((indicatorValue.toFloat() / maxIndicatorValue) * 100 * 3)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("UV Index", style = MaterialTheme.typography.h2)

        Column(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .drawBehind {
                    val componentSize = size / 1.25f
                    backgroundIndicator(
                        componentSize = componentSize,
                        indicatorColor = backgroundIndicatorColor,
                        indicatorStrokeWidth = backgroundIndicatorStrokeWidth,
                    )

                    foregroundIndicator(
                        sweepAngle = percentage,
                        componentSize = componentSize,
                        indicatorColor = foregroundIndicatorColor,
                        indicatorStrokeWidth = foregroundIndicatorStrokeWidth,
                    )

                    backgroundIndicator(
                        componentSize = size / 1.45f,
                        indicatorColor = foregroundIndicatorColor,
                        indicatorStrokeWidth = 5f,
                        dashPathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 70f), 0f)
                    )

                    val sweepAngleRadians = Math.toRadians(120.0 + percentage.toDouble())
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val radius = (size.width / 2) - 45

                    val endX = centerX + (radius * cos(sweepAngleRadians)).toFloat()
                    val endY = centerY + (radius * sin(sweepAngleRadians)).toFloat()

                    drawCircle(
                        color = Color.White,
                        radius = 5f,
                        center = Offset(endX, endY)
                    )
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProgressBarValue(
                value = indicatorValue,
                uvDescription = uvDescription
            )
        }
    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
    dashPathEffect: PathEffect? = null,
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 120f,
        sweepAngle = 300f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round,
            pathEffect = dashPathEffect,
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 120f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

@Composable
private fun ProgressBarValue(
    value: Int,
    uvDescription: String
) {
    Text(
        text = "${value / 10.0} \n\n $uvDescription",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h1
    )
}

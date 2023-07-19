package ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.Black
import ui.theme.Blue
import ui.theme.Pink

@Composable
fun BlurredCard(
    modifier: Modifier = Modifier,
    color: Color = Color.DarkGray,
    blurBackground: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val gradient = Brush.linearGradient(
        listOf(
            Black.copy(alpha = 0.1f),
            Pink.copy(alpha = 0.2f),
            Blue.copy(alpha = 0.2f),
            Black.copy(alpha = 0.1f),
        ),
    )

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (blurBackground != null) {
            blurBackground()
        } else {
            Box(
                modifier = Modifier
                    .blur(70.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                    .matchParentSize()
                    .drawBehind {
                        drawCircle(
                            brush = gradient,
                            radius = size.width * 0.5f,
                        )
                    }
            )
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(24.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .alpha(0.2f)
                .background(color = color)
        )
        content()
    }
}


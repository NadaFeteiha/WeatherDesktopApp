package ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import ui.theme.*

@Composable
fun BlurredCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val gradient = Brush.horizontalGradient(
        listOf(
            Black.copy(alpha = 0.1f),
            Yellow.copy(alpha = 0.2f),
            Blue.copy(alpha = 0.2f),
            Orange.copy(alpha = 0.2f),
            Black.copy(alpha = 0.1f),
        )
    )

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}


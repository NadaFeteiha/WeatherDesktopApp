package composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ui.theme.grey

@Composable
fun BlurredCard(
    modifier: Modifier = Modifier,
    content: @Composable (modifier: Modifier) -> Unit
) {
    Box(
        modifier = modifier.clip(shape = RoundedCornerShape(24.dp))
    ) {
        content(
            modifier = Modifier.background(
                color = grey.copy(alpha = 0.4f),
                shape = RoundedCornerShape(24.dp)
            )
        )

        Image(
            painter = painterResource("bg.svg"),
            contentDescription = null,
        )
    }
}


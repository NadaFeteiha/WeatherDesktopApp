package composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ui.theme.grey

@Composable
fun HourlyItem(
    modifier: Modifier = Modifier,
    icon: String,
    title: String,
    weatherType: String,
    value: String,

    ) {

    Column(
        modifier = modifier.background(
            color = grey.copy(alpha = 0.4f),
            shape = RoundedCornerShape(24.dp)
        ).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
            )
            Text(title, style = MaterialTheme.typography.h2)
        }
        Text("$value   $weatherType", style = MaterialTheme.typography.h1)
    }
}
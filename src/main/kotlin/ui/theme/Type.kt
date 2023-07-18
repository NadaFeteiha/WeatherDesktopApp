package ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val WeatherTypography = Typography(
    h1 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.White
    ),
    h2 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        color = LightGrey
    ),
    h3 = TextStyle(
        fontSize = 48.sp,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        color = White,
    ),
    h4 = TextStyle(
        fontSize = 40.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.SansSerif,
        color = White,
    ),
)


val TitleLarge = SpanStyle(

)

val TitleSmall = SpanStyle(

)
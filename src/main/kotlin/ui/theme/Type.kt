package ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp


val SFPro = FontFamily(
    Font("font/sf_medium.ttf", weight = FontWeight.Medium),
    Font("font/sf_regular.ttf", weight = FontWeight.Normal),
)

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
    body1 = TextStyle(
        fontFamily = SFPro,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = LightGrey,
    ),
    body2 = TextStyle(
        fontFamily = SFPro,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = LightGrey,
    )
)


val TitleLarge = SpanStyle(

)

val TitleSmall = SpanStyle(

)

val maxTemperatureSpanStyle = SpanStyle(
    fontFamily = SFPro,
    fontSize = 22.sp,
    fontWeight = FontWeight.Medium,
    color = Color(0xDEFFFFFF),
)

val minTemperatureSpanStyle = SpanStyle(
    fontFamily = SFPro,
    fontSize = 18.sp,
    fontWeight = FontWeight.Normal,
    color = Color(0x99FFFFFF),
)
package data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Day(
    @SerialName("avghumidity")
    val avghumidity: Double?,
    @SerialName("avgtemp_c")
    val avgtempC: Double?,
    @SerialName("avgtemp_f")
    val avgtempF: Double?,
    @SerialName("avgvis_km")
    val avgvisKm: Double?,
    @SerialName("avgvis_miles")
    val avgvisMiles: Double?,
    @SerialName("condition")
    val condition: Condition?,
    @SerialName("daily_chance_of_rain")
    val dailyChanceOfRain: Int?,
    @SerialName("daily_chance_of_snow")
    val dailyChanceOfSnow: Int?,
    @SerialName("daily_will_it_rain")
    val dailyWillItRain: Int?,
    @SerialName("daily_will_it_snow")
    val dailyWillItSnow: Int?,
    @SerialName("maxtemp_c")
    val maxtempC: Double?,
    @SerialName("maxtemp_f")
    val maxtempF: Double?,
    @SerialName("maxwind_kph")
    val maxwindKph: Double?,
    @SerialName("maxwind_mph")
    val maxwindMph: Double?,
    @SerialName("mintemp_c")
    val mintempC: Double?,
    @SerialName("mintemp_f")
    val mintempF: Double?,
    @SerialName("totalprecip_in")
    val totalprecipIn: Double?,
    @SerialName("totalprecip_mm")
    val totalprecipMm: Double?,
    @SerialName("totalsnow_cm")
    val totalsnowCm: Double?,
    @SerialName("uv")
    val uv: Double?
)
package data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    @SerialName("current")
    val current: Current?,
//    @SerialName("forecast")
//    val forecast: Forecast?,
    @SerialName("location")
    val location: Location?
)
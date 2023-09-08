package data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Forecastday(
    @SerialName("astro")
    val astro: Astro?,
    @SerialName("date")
    val date: String?,
    @SerialName("date_epoch")
    val dateEpoch: Int?,
    @SerialName("day")
    val day: Day?,
    @SerialName("hour")
    val hour: List<Hour>?
)
package data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchItem(
    @SerialName("country")
    val country: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("lat")
    val lat: Double?,
    @SerialName("lon")
    val lon: Double?,
    @SerialName("name")
    val name: String?,
    @SerialName("region")
    val region: String?,
    @SerialName("url")
    val url: String?
)
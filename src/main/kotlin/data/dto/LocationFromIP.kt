package data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationFromIP(
    @SerialName("borders")
    val borders: List<String>,
    @SerialName("capital")
    val capital: String,
    @SerialName("city")
    val city: String,
    @SerialName("cityGeoNameId")
    val cityGeoNameId: Int,
    @SerialName("continentCode")
    val continentCode: String,
    @SerialName("continentName")
    val continentName: String,
    @SerialName("countryCode")
    val countryCode: String,
    @SerialName("countryFlagEmoj")
    val countryFlagEmoj: String,
    @SerialName("countryFlagEmojUnicode")
    val countryFlagEmojUnicode: String,
    @SerialName("countryName")
    val countryName: String,
    @SerialName("countryNameNative")
    val countryNameNative: String,
    @SerialName("ip")
    val ip: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("officialCountryName")
    val officialCountryName: String,
    @SerialName("phoneCode")
    val phoneCode: String,
    @SerialName("regionCode")
    val regionCode: String,
    @SerialName("regionName")
    val regionName: String,
    @SerialName("topLevelDomains")
    val topLevelDomains: List<String>
)
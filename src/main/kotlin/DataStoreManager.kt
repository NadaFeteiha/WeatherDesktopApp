import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.extensions.storeOf
import kotlinx.serialization.Serializable


@Serializable
data class Location(val city: String, val country: String)

val store: KStore<List<Location>> =
    storeOf(
        "/Users/nadafeteiha/Desktop/TheChance102/WeatherDesktopApp/my_location.json",
        default = null,
        enableCache = true,
        version = 0
    )

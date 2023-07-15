package controller

import data.remote.WeatherService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

data class HomeUIState(
    val isLoading: Boolean = false,
    val data: String = "",
    val error: String = ""
)


interface HomeInteractionListener {
    fun getData()
}

class HomeController(private val service: WeatherService) : HomeInteractionListener, KoinComponent {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    override fun getData() {
        _uiState.update { it.copy(isLoading = true) }
        CoroutineScope(Dispatchers.IO).launch {
            _uiState.update { it.copy(isLoading = false, data = service.getWeatherByCityName("London").toString()) }
        }
    }

}
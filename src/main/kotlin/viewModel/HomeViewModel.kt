package viewModel

import data.remote.WeatherService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(private val service: WeatherService) : HomeInteractionListener, KoinComponent {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _uiState.emit(service.getWeatherByCityName("lynnwood").toUIState())
            formatDate()
        }
    }

    override fun getData() {

    }

    private fun formatDate() {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM, yyyy h:mm a", Locale.getDefault())

        CoroutineScope(Dispatchers.IO).launch {
            val inputDate = service.getWeatherByCityName("lynnwood").location?.localtime
            println("$inputDate")
            val parsedDate = inputFormat.parse(inputDate)
            val outputDate = outputFormat.format(parsedDate)
            _uiState.update { it.copy(date =outputDate) }
        }
    }


}
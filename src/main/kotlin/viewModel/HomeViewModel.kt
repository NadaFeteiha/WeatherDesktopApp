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

    override fun search(keyword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val cities = service.searchWeatherByCityName(keyword).mapNotNull { it.name }
            println("======================= \n \n $cities")
            _uiState.update { it.copy(suggestion = cities, isExpandMenuSuggestion = cities.isNotEmpty()) }
        }
    }

    override fun onDropDownMenuExpand(expand: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            _uiState.update { it.copy(isExpandMenuSuggestion = expand) }
        }
    }

    private fun formatDate() {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM, yyyy h:mm a", Locale.getDefault())

        CoroutineScope(Dispatchers.IO).launch {
            val inputDate = service.getWeatherByCityName("lynnwood").location?.localtime
            val parsedDate = inputFormat.parse(inputDate)
            val outputDate = outputFormat.format(parsedDate)
            _uiState.update { it.copy(date = outputDate) }
        }
    }


}
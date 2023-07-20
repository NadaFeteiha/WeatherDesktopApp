package viewModel

import data.remote.WeatherService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class HomeViewModel(private val service: WeatherService) : HomeInteractionListener, KoinComponent {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            _uiState.update { it.copy(isLoading = true, error = "") }
            try {
                _uiState.emit(service.getWeatherByCityName(service.getLocation().city).toUIState())
            } catch (e: Throwable) {
                _uiState.update { it.copy(isLoading = false, error = e.message.toString()) }
            }
        }
    }

    override fun onSearchExpand(state: Boolean) {
        _uiState.update { it.copy(isSearchExpanded = state) }
    }

    override fun search(keyword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _uiState.update { it.copy(keyword = keyword, isSearchExpanded = true) }
            if (keyword.trim().isNotEmpty()) {
                val cities = service.searchWeatherByCityName(keyword).mapNotNull { it.name }
                _uiState.update { it.copy(suggestion = cities, isExpandMenuSuggestion = cities.isNotEmpty()) }
            }
        }
    }

    override fun onDropDownMenuExpand(expand: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            _uiState.update { it.copy(isExpandMenuSuggestion = expand) }
        }
    }

    override fun onSearchCitySelected(city: String) {
        _uiState.update { it.copy(isLoading = true, isSearchExpanded = false) }
        CoroutineScope(Dispatchers.IO).launch {
            _uiState.emit(service.getWeatherByCityName(city).toUIState())
        }
    }

}
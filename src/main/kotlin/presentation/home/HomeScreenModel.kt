package presentation.home

import data.WeatherService
import data.dto.SearchItem
import data.dto.Weather
import kotlinx.coroutines.Job
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class HomeScreenModel(private val service: WeatherService) : BaseScreenModel<HomeUIState, HomeUiEffect>(HomeUIState()),
    HomeInteractionListener {

    private var searchJob: Job? = null

    init {
        getData()
    }

    override fun getData() {
        updateState { it.copy(isLoading = true, error = null) }
        tryToExecute(
            { service.getWeatherByCityName(service.getLocation().city) },
            onSuccess = ::onGetWeatherByCitySuccess,
            onError = ::onError
        )
    }

    private fun onGetWeatherByCitySuccess(weather: Weather) {
        updateState { it.copy(weatherUIState = weather.toUIState(), isLoading = false, error = null) }
    }

    override fun onSearchExpand(state: Boolean) {
        updateState { it.copy(isSearchExpanded = state) }
    }

    private fun onSearchSuccess(searchItems: List<SearchItem>) {
        val cities = searchItems.mapNotNull { it.name }
        updateState {
            it.copy(
                isSearchExpanded = true,
                suggestion = cities,
                isExpandMenuSuggestion = cities.isNotEmpty()
            )
        }
    }

    override fun onSearchTermChanged(term: String) {
        updateState { it.copy(keyword = term) }
        launchSearchJob()
    }

    override fun search() {
        tryToExecute(
            { service.searchWeatherByCityName(state.value.keyword) },
            onSuccess = ::onSearchSuccess,
            onError = ::onError
        )
    }

    private fun launchSearchJob() {
        searchJob?.cancel()
        searchJob = launchDelayed(300L) { search() }
    }

    override fun onDropDownMenuExpand(expand: Boolean) {
        updateState { it.copy(isExpandMenuSuggestion = expand) }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isLoading = false) }
    }

    override fun onSearchCitySelected(city: String) {
        updateState { it.copy(isLoading = true, isSearchExpanded = false) }
        tryToExecute(
            { service.getWeatherByCityName(city) },
            onSuccess = ::onGetWeatherByCitySuccess,
            onError = ::onError
        )
    }
}
package viewModel

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import data.remote.WeatherService
import data.remote.dto.SearchItem
import data.remote.dto.Weather
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.update

class HomeScreenModel(private val service: WeatherService) : StateScreenModel<HomeUIState>(HomeUIState()),
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

    private fun <T> tryToExecute(
        callee: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (ErrorState) -> Unit,
        inScope: CoroutineScope = coroutineScope,
    ): Job {
        return runWithErrorCheck(inScope = inScope, onError = onError) {
            val result = callee()
            onSuccess(result)
        }
    }

    private fun <T> runWithErrorCheck(
        inScope: CoroutineScope = coroutineScope,
        onError: (ErrorState) -> Unit,
        callee: suspend () -> T,
    ): Job {
        return inScope.launch(Dispatchers.IO) {
            callee()
            try {
            } catch (exception: Exception) {
                onError(ErrorState.UnKnownError)
            }
        }
    }

    private fun updateState(updater: (HomeUIState) -> HomeUIState) {
        mutableState.update(updater)
    }

    private fun launchDelayed(duration: Long, block: suspend CoroutineScope.() -> Unit): Job {
        return coroutineScope.launch(Dispatchers.IO) {
            delay(duration)
            block()
        }
    }

}
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            store.set(
                listOf(
                    Location("Seattle", "USA"),
                    Location("Cairo", "Egypt")
                )
            )
        }
    }

    fun getData() {
        viewModelScope.launch {
            val data = store.get()
            _uiState.update { it.copy(data = data.toString()) }
        }
    }


}


data class HomeUIState(
    val isLoading: Boolean = true,
    val data: String = ""
)
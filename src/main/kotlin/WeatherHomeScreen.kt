import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun WeatherHomeScreen() {
    val viewModel = viewModel(HomeViewModel::class) {
        HomeViewModel()
    }
    val state by viewModel.uiState.collectAsState()

    Column {
        Text(state.data)

        Button(onClick = {
            viewModel.getData()
        }) {
            Text("Get Data")
        }

        Button(onClick = {
            viewModel.setData()
        }) {
            Text("Add more Data")
        }

    }


}
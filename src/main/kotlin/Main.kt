// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.remote.WeatherServiceImpl
import kotlinx.coroutines.launch

@Composable
@Preview
fun App(controller: HomeController = initKoin().koin.get()) {
    val state by controller.uiState.collectAsState()
    MaterialTheme {
        Column {
            Button(onClick = controller::getData) {
                Text("Get Data")
            }
            if (state.isLoading) {
                Text(
                    "Loading ...",
                    style = TextStyle(fontSize = 18.sp)
                )
            }

            Text(state.data)
        }
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

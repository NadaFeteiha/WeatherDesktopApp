// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import composables.Compass
import composables.CompassUiState

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    val coroutine = rememberCoroutineScope()

    MaterialTheme {

        Compass(CompassUiState(windDegree = -45f, windKph = 10f))

//        Button(onClick = {
//            coroutine.launch {
//                print(WeatherServiceImpl().getWeatherByCityName("London").location?.name)
//            }
//        }) {
//            Text(text)
//        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

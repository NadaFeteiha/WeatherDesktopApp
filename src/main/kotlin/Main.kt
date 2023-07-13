// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import data.remote.WeatherServiceImpl
import kotlinx.coroutines.launch
import moe.tlaster.precompose.PreComposeWindow

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    val coroutine = rememberCoroutineScope()

    MaterialTheme {
        Column {
            Button(onClick = {
                coroutine.launch {
                    print(WeatherServiceImpl().getWeatherByCityName("London").toString())
                }
            }) {
                Text(text)
            }

        }
    }
}

fun main() = application {
    PreComposeWindow(onCloseRequest = ::exitApplication) {
        App()
    }
}

// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import composable.SunriseSunsetView
import data.remote.WeatherServiceImpl
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    var text by remember {
        mutableStateOf("Hello, World!")

    }

    val coroutine = rememberCoroutineScope()

    MaterialTheme {
        Card {
            SunriseSunsetView()
            Text(text)
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

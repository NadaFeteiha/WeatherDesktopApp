// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import di.initKoin
import ui.HomeScreen
import ui.theme.LightColor
import ui.theme.WeatherTypography
import viewModel.HomeViewModel
import java.awt.Dimension

@Composable
@Preview
fun App(windowState: WindowState, viewModel: HomeViewModel = initKoin().koin.get()) {
    val state by viewModel.uiState.collectAsState()
    MaterialTheme(
        colors = LightColor,
        typography = WeatherTypography
    ) {
        Surface(color = MaterialTheme.colors.background) {
            HomeScreen(
                state = state,
                listener = viewModel,
                windowState = windowState
            )
        }
    }
}

fun main() = application {
    val windowState = rememberWindowState()
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
    ) {
        this.window.minimumSize = Dimension(800, 600)
        App(windowState)
    }
}

// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import di.appModules
import org.koin.core.context.startKoin
import presentation.home.HomeScreen
import presentation.theme.LightColor
import presentation.theme.WeatherTypography
import java.awt.Dimension

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme(
        colors = LightColor,
        typography = WeatherTypography
    ) {
        Surface(color = MaterialTheme.colors.background) {
            Navigator(HomeScreen) {
                SlideTransition(it)
            }
        }
    }
}

fun main() {
    startKoin { modules(appModules()) }
    application {
        val windowState = rememberWindowState()
        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Weather Application"
        ) {
            this.window.minimumSize = Dimension(1250, 800)
            App()
        }
    }
}

// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.application
import moe.tlaster.precompose.PreComposeWindow
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
@Preview
fun App() {
    val navigator = rememberNavigator()
    MaterialTheme {
        NavHost(
            navigator = navigator,
            initialRoute = "/home"
        ) {
            scene("/home") {
                WeatherHomeScreen(
//                    onItemClicked = {
//                        navigator.navigate("/detail/${it.id}")
//                    }
                )
            }
            scene("/detail/{id:[0-9]+}") { backStackEntry ->
                backStackEntry.path<Int>("id")?.let {
//                    NoteDetailScene(
//                        id = it,
//                        onEdit = {
//                            navigator.navigate("/edit/$it")
//                        },
//                        onBack = {
//                            navigator.goBack()
//                        },
//                    )
                }
            }
        }
    }
}

fun main() = application {
    PreComposeWindow(onCloseRequest = ::exitApplication) {
        App()
    }
}

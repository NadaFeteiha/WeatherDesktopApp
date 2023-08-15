// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.window.*
import com.sun.javafx.application.PlatformImpl
import di.initKoin
import javafx.application.Application
import javafx.application.Platform
import javafx.concurrent.Worker
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import javafx.stage.Stage
import netscape.javascript.JSObject
import ui.theme.LightColor
import ui.theme.WeatherTypography
import viewModel.HomeViewModel
import java.awt.BorderLayout
import java.awt.Dimension
import java.net.http.WebSocket
import javax.swing.JPanel

@Composable
@Preview
fun App(windowState: WindowState, viewModel: HomeViewModel = initKoin().koin.get()) {
    val state by viewModel.uiState.collectAsState()
    MaterialTheme(
        colors = LightColor,
        typography = WeatherTypography
    ) {
        Surface(color = MaterialTheme.colors.background) {
//            HomeScreen(
//                state = state,
//                listener = viewModel,
//                windowState = windowState,
//            )
            MapScreen()
        }
    }
}

@Composable
fun MapScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Map")
        val googleMapsUrl = "https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY"


    }


}

fun main() = application {
    val windowState = rememberWindowState()
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Weather Application"
    ) {
        this.window.minimumSize = Dimension(1250, 800)
//        App(windowState)
        val jfxPanel = remember { JFXPanel() }
        Column {
            ComposeJFXPanel(
                jfxPanel = jfxPanel,
                onCreate = {},
                onDestroy = {}
            )
        }

    }
}


////////////////////
fun main2() = application(exitProcessOnExit = true) {

    val finishListener = object : PlatformImpl.FinishListener {
        override fun idle(implicitExit: Boolean) {}
        override fun exitCalled() {}
    }
    PlatformImpl.addListener(finishListener)

    Window(
        title = "WebView Test",
        resizable = false,
        state = WindowState(
            placement = WindowPlacement.Floating,
//            size = DpSize(1200.dp, 800.dp)
        ),
        onCloseRequest = {
            PlatformImpl.removeListener(finishListener)
            exitApplication()
        },
        content = {
            val jfxPanel = remember { JFXPanel() }
            var jsObject = remember<JSObject?> { null }

            Box(modifier = Modifier.fillMaxSize().background(Color.White)) {

                ComposeJFXPanel(
//                    composeWindow = window,
                    jfxPanel = jfxPanel,
                    onCreate = {
                        Platform.runLater {
                            val root = WebView()
                            val engine = root.engine
                            val scene = Scene(root)
                            engine.loadWorker.stateProperty().addListener { _, _, newState ->
                                if (newState === Worker.State.SUCCEEDED) {
                                    jsObject = root.engine.executeScript("window") as JSObject
                                    // execute other javascript / setup js callbacks fields etc..
                                }
                            }
                            engine.loadWorker.exceptionProperty().addListener { _, _, newError ->
                                println("page load error : $newError")
                            }
                            jfxPanel.scene = scene
                            engine.load("http://google.com") // can be a html document from resources ..
                            engine.setOnError { error -> println("onError : $error") }
                        }
                    }, onDestroy = {
                        Platform.runLater {
                            jsObject?.let { jsObj ->
                                // clean up code for more complex implementations i.e. removing javascript callbacks etc..
                            }
                        }
                    })
            }
        })
}


@Composable
fun ComposeJFXPanel(
//    composeWindow: ComposeWindow,
    jfxPanel: JFXPanel,
    onCreate: () -> Unit,
    onDestroy: () -> Unit = {}
) {
    val jPanel = remember { JPanel() }
    val density = LocalDensity.current.density

    Layout(
        content = {},
        modifier = Modifier.onGloballyPositioned { childCoordinates ->
            val coordinates = childCoordinates.parentCoordinates!!
            val location = coordinates.localToWindow(Offset.Zero).round()
            val size = coordinates.size
            jPanel.setBounds(
                (location.x / density).toInt(),
                (location.y / density).toInt(),
                (size.width / density).toInt(),
                (size.height / density).toInt()
            )
            jPanel.validate()
            jPanel.repaint()
        },
        measurePolicy = { _, _ -> layout(0, 0) {} })

//    DisposableEffect(jPanel) {
//        composeWindow.add(jPanel)
//        jPanel.layout = BorderLayout(0, 0)
//        jPanel.add(jfxPanel)
//        onCreate()
//        onDispose {
//            onDestroy()
//            composeWindow.remove(jPanel)
//        }
//    }
}
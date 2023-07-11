import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.application
import moe.tlaster.precompose.PreComposeWindow
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator

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

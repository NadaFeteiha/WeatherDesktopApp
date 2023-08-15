import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import javafx.application.Platform
import javafx.concurrent.Worker
import javafx.embed.swing.JFXPanel
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.web.WebEngine
import javafx.scene.web.WebEvent
import javafx.scene.web.WebView
import kotlinx.serialization.Serializable
import java.awt.BorderLayout
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.border.EmptyBorder


val Gray = java.awt.Color(64, 64, 64)
val DarkGray = java.awt.Color(32, 32, 32)
val LightGray = java.awt.Color(210, 210, 210)

fun java.awt.Color.toCompose(): Color {
    return Color(red, green, blue)
}

class JavaScriptInterface {
    fun getJsObject(): String {
        return """
            {
                markerClickEvent: function(coordinatesJson) {
                    var latLng = JSON.parse(coordinatesJson);
                    java.markerClickEvent(latLng.lat, latLng.lng);
                },
                showMarkerChangeAlert: function(message) {
                    alert(message);
                }
            }
        """.trimIndent()
    }

    fun markerClickEvent(lat: Double, lng: Double) {
        // Handle the latitude and longitude as needed in your Swing component
        // For example, you can print the coordinates to the console
        println("Marker clicked at Lat: $lat, Lng: $lng")
    }
}

@Serializable
data class LatLng(val lat: Double, val lng: Double)

fun markerClickEvent(lat: Double, lng: Double) {
    // Handle the latitude and longitude as needed in your Swing component
    // For example, you can print the coordinates to the console
    println("Marker clicked at Lat: $lat, Lng: $lng")
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 1000.dp, height = 1500.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(color = Gray.toCompose()).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Compose Area", color = LightGray.toCompose())
            Spacer(modifier = Modifier.height(40.dp))
            SwingPanel(
                background = DarkGray.toCompose(),
                modifier = Modifier.fillMaxSize(),
                factory = {
                    SwingComponent4()
                }
            )
        }
    }
}


fun SwingComponent4(): JPanel {
    val jPanel = JPanel().apply {
        background = DarkGray
        border = EmptyBorder(20, 20, 20, 20)
        layout = BorderLayout()

        // Create a JFXPanel for embedding JavaFX components
        val jfxPanel = JFXPanel()
        add(jfxPanel, BorderLayout.CENTER)

        Platform.runLater {
            val webView = WebView()
            val webEngine: WebEngine = webView.engine
            val scene = Scene(webView)
            jfxPanel.scene = scene
            val htmlResource = Testttt::class.java.getResource("/testweb.html")
            val htmlContent = htmlResource?.readText()?.trimIndent()

            val javaScriptInterface = JavaScriptInterface()
            webEngine.loadContent(htmlContent)
            val jsObject = javaScriptInterface.getJsObject()
            webEngine.executeScript(jsObject)
        }
    }
    SwingUtilities.invokeLater {
        jPanel.revalidate()
        jPanel.repaint()
    }
    return jPanel
}

class Testttt {

}








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
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState


import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.border.EmptyBorder
import java.awt.BorderLayout
import java.awt.Color

fun SwingComponent3(): JPanel {

    val jPanel = JPanel().apply {
        background = Color.GRAY
        border = EmptyBorder(20, 20, 20, 20)
        layout = BorderLayout()

        // Create a JFXPanel for embedding JavaFX components
        val jfxPanel = JFXPanel()
        add(jfxPanel, BorderLayout.CENTER)

//        background = Color.GRAY
        border = EmptyBorder(20, 20, 20, 20)
        layout = BorderLayout()

        Platform.runLater {
            val webView = WebView()
            val webEngine: WebEngine = webView.engine
            val scene = Scene(webView)
            jfxPanel.scene = scene

            val javaBridge = JavaBridge()
            webEngine.executeScript("window.javaBridge = $javaBridge;")

            webEngine.loadContent(
                """
                <!DOCTYPE html>
                <html>
                <head>
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/leaflet.js"></script>
                    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/leaflet.css" />
                    <style>
                        #map-canvas {
                            width: 800px;
                            height: 600px;
                        }
                    </style>
                    <script>
                        var map;
                        var marker;

                        function initialize() {
                            map = L.map('map-canvas').setView([47.812972, -122.2228419], 15);
                            L.tileLayer('https://{s}.tile.thunderforest.com/neighbourhood/{z}/{x}/{y}.png?apikey=YOUR_API_KEY', {
                                attribution: '&copy; <a href="https://www.thunderforest.com/">Thunderforest</a>'
                            }).addTo(map);
                            
                            map.on('click', function(e) {
                                if (marker) {
                                    map.removeLayer(marker);
                                }
                                placeMarker(e.latlng);
                                getLocation(e.latlng);
                            });
                        }

                        function placeMarker(location) {
                            marker = L.marker(location).addTo(map);
                        }

                        function getLocation(location) {
                            var lat = location.lat;
                            var lng = location.lng;
                            javaBridge.setLocation(lat, lng);
                        }

                        initialize();
                    </script>
                </head>
                <body>
                    <div id="map-canvas"></div>
                    <div id="location-info">Click on the map to get location</div>
                </body>
                </html>
                """.trimIndent())
        }
    }
    return jPanel
}

class JavaBridge {
    fun setLocation(lat: Double, lng: Double) {
        Platform.runLater {
//            val webEngine = SwingComponent3().webEngine
//            webEngine.executeScript(
//                "document.getElementById('location-info').innerHTML = 'Latitude: ' + $lat + '<br>Longitude: ' + $lng;"
//            )
        }
    }
}


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 1000.dp, height = 1500.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(color = Gray).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Compose Area", color = LightGray)
            Spacer(modifier = Modifier.height(40.dp))
            SwingPanel(
                background = DarkGray,
                modifier = Modifier.fillMaxSize(),
                factory = {
                    SwingComponent3()
                }
            )
        }
    }
}
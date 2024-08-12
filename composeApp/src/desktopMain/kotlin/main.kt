import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import core.di.KoinInitializer

fun main() = application {
    KoinInitializer().init()
    Window(
        onCloseRequest = ::exitApplication,
        title = "deficit",
    ) {
        App()
    }
}
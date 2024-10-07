import androidx.compose.ui.window.ComposeUIViewController
import core.di.KoinInitializer
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun MainViewController() = ComposeUIViewController(
    configure = {
        Napier.base(DebugAntilog())
        KoinInitializer().init()
    }
) {
    App()
}
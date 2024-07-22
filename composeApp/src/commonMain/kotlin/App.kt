import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import core.ui.navigation.RootNavHost
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            val navController = rememberNavController()
            RootNavHost(navController = navController)
        }
    }
}

@Composable
inline fun <reified T : ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel { scope.get<T>() }
}

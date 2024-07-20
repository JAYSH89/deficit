import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import core.ui.navigation.RootNavHost
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import deficit.composeapp.generated.resources.Res
import deficit.composeapp.generated.resources.buy
import deficit.composeapp.generated.resources.compose_multiplatform
import deficit.composeapp.generated.resources.home

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    MaterialTheme {
        RootNavHost(navController = navController)
    }
}
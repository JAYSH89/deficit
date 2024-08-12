package feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import core.ui.navigation.HomeNavHost

@Composable
fun HomeScreen(
    rootNavController: NavController,
    homeNavController: NavHostController = rememberNavController(),
) {
    val verticalGradient = Brush.verticalGradient(
        colors = listOf(
            Color(color = 0xFFE7EBFF),
            Color(color = 0xFFF7DDED),
            Color(color = 0xFFFFFFFF),
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = verticalGradient)
            .statusBarsPadding(),
    ) {
        Scaffold(
            bottomBar = { HomeBottomNavigation(navController = homeNavController) },
            containerColor = Color.Transparent,
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                content = {
                    HomeNavHost(
                        rootNavController = rootNavController,
                        homeNavController = homeNavController,
                    )
                }
            )
        }
    }
}

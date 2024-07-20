package feature.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import core.ui.navigation.HomeNavHost

@Composable
fun HomeScreen(
    rootNavController: NavController,
    homeNavController: NavHostController = rememberNavController(),
) {
    Scaffold(
        bottomBar = {
            HomeBottomNavigation(navController = homeNavController)
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
        ) {
            HomeNavHost(
                rootNavController = rootNavController,
                homeNavController = homeNavController,
            )
        }
    }
}

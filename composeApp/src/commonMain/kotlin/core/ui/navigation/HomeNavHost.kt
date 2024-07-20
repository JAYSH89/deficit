package core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import feature.food.presentation.FoodScreen
import feature.journal.presentation.JournalScreen
import feature.overview.presentation.OverviewScreen
import feature.settings.presentation.SettingsScreen

@Composable
fun HomeNavHost(rootNavController: NavController, homeNavController: NavHostController) {
    NavHost(navController = homeNavController, startDestination = Destination.OVERVIEW) {
        composable(Destination.OVERVIEW) {
            OverviewScreen()
        }

        composable(Destination.FOOD) {
            FoodScreen()
        }

        composable(Destination.JOURNAL) {
            JournalScreen()
        }

        composable(Destination.SETTINGS) {
            SettingsScreen()
        }
    }
}
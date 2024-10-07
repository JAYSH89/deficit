package core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import feature.food.FoodDetailScreen
import feature.food.FoodOverviewScreen
import feature.journal.JournalOverviewScreen
import feature.overview.OverviewScreen
import feature.settings.SettingsScreen

@Composable
fun HomeNavHost(rootNavController: NavController, homeNavController: NavHostController) {
    NavHost(navController = homeNavController, startDestination = Destination.OVERVIEW) {
        composable(Destination.OVERVIEW) {
            OverviewScreen()
        }

        composable(Destination.FOOD_OVERVIEW) {
            FoodOverviewScreen(
                onSelectFood = { foodId ->
                    if (foodId == null) {
                        homeNavController.navigate(Destination.CREATE_FOOD)
                    } else {
                        homeNavController.navigate(Destination.foodDetail(foodId = foodId))
                    }
                }
            )
        }

        composable(
            route = "${Destination.FOOD_DETAIL}?foodId={foodId}",
            arguments = listOf(
                navArgument(name = "foodId") { type = NavType.LongType },
            )
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getLong("foodId")
            FoodDetailScreen(
                foodId = foodId,
                onClickBack = { homeNavController.navigateUp() },
            )
        }

        composable(route = Destination.CREATE_FOOD) {
            FoodDetailScreen(onClickBack = { homeNavController.navigateUp() })
        }

        composable(Destination.JOURNAL_OVERVIEW) {
            JournalOverviewScreen()
        }

        composable(Destination.SETTINGS) {
            SettingsScreen()
        }
    }
}
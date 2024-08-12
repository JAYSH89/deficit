package feature.home.presentation

import core.ui.navigation.Destination
import deficit.composeapp.generated.resources.Res
import deficit.composeapp.generated.resources.buy
import deficit.composeapp.generated.resources.chart
import deficit.composeapp.generated.resources.cogwheel
import deficit.composeapp.generated.resources.home
import org.jetbrains.compose.resources.DrawableResource

sealed class BottomNavigationData(
    val route: String,
    val title: String,
    val icon: DrawableResource,
) {
    data object Overview : BottomNavigationData(
        route = Destination.OVERVIEW,
        title = "Overview",
        icon = Res.drawable.home,
    )

    data object Food : BottomNavigationData(
        route = Destination.FOOD_OVERVIEW,
        title = "Food",
        icon = Res.drawable.buy,
    )

    data object Journal : BottomNavigationData(
        route = Destination.JOURNAL_OVERVIEW,
        title = "Journal",
        icon = Res.drawable.chart,
    )

    data object Settings : BottomNavigationData(
        route = Destination.SETTINGS,
        title = "Settings",
        icon = Res.drawable.cogwheel,
    )
}

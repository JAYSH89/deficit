package feature.home.presentation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import deficit.composeapp.generated.resources.Res
import deficit.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeBottomNavigation(navController: NavHostController) {
    val screens = remember {
        listOf(
            BottomNavigationData.Overview,
            BottomNavigationData.Food,
            BottomNavigationData.Journal,
            BottomNavigationData.Settings,
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val tabBarDestination = screens.any { it.route == currentDestination?.route }

    if (tabBarDestination) {
        BottomNavigation {
            screens.forEach { screen ->
                BottomNavigationItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController,
                )
            }
        }
    }
}

@Composable
private fun RowScope.BottomNavigationItem(
    screen: BottomNavigationData,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    BottomNavigationItem(
        selected = selected,
        label = { BottomNavigationLabel(text = screen.title) },
        icon = {
            Icon(
                painter = painterResource(resource = screen.icon),
                contentDescription = screen.title,
            )
        },
        onClick = {
            if (!selected) navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().navigatorName)
                launchSingleTop = true
            }
        },
    )
}

@Composable
private fun BottomNavigationLabel(text: String) = Text(
    text = text,
)

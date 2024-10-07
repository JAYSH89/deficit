package feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
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
        NavigationBar(
            containerColor = Color.White,
            contentColor = Color.Black,
            tonalElevation = 4.dp,
        ) {
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

    val navigationBarItemColors = NavigationBarItemColors(
        selectedIconColor = LocalContentColor.current,
        selectedTextColor = LocalContentColor.current,
        selectedIndicatorColor = Color.Transparent,
        unselectedIconColor = LocalContentColor.current.copy(alpha = 0.3f),
        unselectedTextColor = LocalContentColor.current.copy(alpha = 0.3f),
        disabledIconColor = Color.Gray,
        disabledTextColor = Color.Gray,
    )

    NavigationBarItem(
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars),
        selected = selected,
        colors = navigationBarItemColors,
        label = {
            BottomNavigationLabel(text = screen.title, selected = selected)
        },
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
private fun BottomNavigationLabel(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(4.dp),
) {
    Text(text = text, style = MaterialTheme.typography.labelSmall)
    Box(
        modifier = Modifier
            .size(4.dp)
            .background(
                color = MaterialTheme.colorScheme.error.copy(
                    alpha = if (selected) 1.0f else 0.0f
                ),
                shape = CircleShape
            ),
    )
}

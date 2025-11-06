package com.example.immunify.ui.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.immunify.R
import com.example.immunify.ui.theme.*

private val bottomItems = listOf(
    BottomNavItem(Routes.HOME, "Home", R.drawable.ic_home),
    BottomNavItem(Routes.CLINICS, "Clinics", R.drawable.ic_clinics),
    BottomNavItem(Routes.TRACKER, "Tracker", R.drawable.ic_tracker),
    BottomNavItem(Routes.PROFILE, "Profile", R.drawable.ic_profile)
)

@Composable
fun BottomNavBar(navController: NavHostController) {
    val backStack by navController.currentBackStackEntryAsState()
    val dest = backStack?.destination

    NavigationBar(
        containerColor = White10,
        tonalElevation = 0.dp,
        modifier = Modifier
            .height(90.dp)
            .padding(horizontal = 8.dp)
    ) {
        bottomItems.forEach { item ->
            val selected = isSelected(dest, item.route)
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        tint = if (selected) PrimaryMain else Grey50,
                        modifier = Modifier.size(28.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = Typography.bodySmall,
                        color = if (selected) PrimaryMain else Grey50
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryMain,
                    unselectedIconColor = Grey50,
                    selectedTextColor = PrimaryMain,
                    unselectedTextColor = Grey50,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}


private fun isSelected(dest: NavDestination?, route: String): Boolean =
    dest?.hierarchy?.any { it.route == route } == true

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    val navController = rememberNavController()
    BottomNavBar(navController)
}
package net.kahlenberger.eberhard.coad

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.kahlenberger.eberhard.coad.screens.Screen
import net.kahlenberger.eberhard.coad.screens.dishes.AddDish
import net.kahlenberger.eberhard.coad.screens.dishes.DishesScreen
import net.kahlenberger.eberhard.coad.screens.home.HomeScreen
import net.kahlenberger.eberhard.coad.uidata.ConfiguredLimits
import net.kahlenberger.eberhard.coad.uidata.DishesViewModel
import net.kahlenberger.eberhard.coad.uidata.MainViewModel

@Composable
fun Navigation(mainViewModel: MainViewModel, dishesViewModel: DishesViewModel) {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Dishes)
    val bottomBarVisible = remember { mutableStateOf(true) }

    // Add Destination change listener
    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            bottomBarVisible.value = destination.route?.let { Screen.values().find{screen -> screen.route == it}!!.title } != ""
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    Scaffold(
        bottomBar = {
            if (bottomBarVisible.value)
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    items.filter{item -> item.title != ""}.forEach { screen ->
                        BottomNavigationItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.route == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
        },
        content = { innerPadding ->
            NavHost(navController = navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) {
                    val dummyConfiguredLimits = ConfiguredLimits(2500, 75f)
                    HomeScreen(mainViewModel, dummyConfiguredLimits, innerPadding)
                }
                composable(Screen.Dishes.route) {
                    DishesScreen(dishesViewModel, innerPadding, navController)
                }
                composable(Screen.AddDishes.route) {
                    AddDish(dishesViewModel, navController)
                }
            }
        }
    )
}
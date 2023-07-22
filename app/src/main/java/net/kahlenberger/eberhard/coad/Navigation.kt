package net.kahlenberger.eberhard.coad

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.kahlenberger.eberhard.coad.screens.Screen
import net.kahlenberger.eberhard.coad.screens.addDishes.AddDish
import net.kahlenberger.eberhard.coad.screens.dishes.DishesScreen
import net.kahlenberger.eberhard.coad.screens.home.HomeScreen
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
            bottomBarVisible.value = destination.route?.let { Screen.values().find{screen -> screen.route == it}!!.titleResId } != 0
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

                    items.filter{item -> item.titleResId != 0}.forEach { screen ->
                        BottomNavigationItem(
                            icon = { Icon(screen.icon, contentDescription = stringResource(screen.titleResId)) },
                            label = { Text(stringResource(screen.titleResId)) },
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
                    //val dummyConfiguredLimits = ConfiguredLimits(2500, 75f)
                    HomeScreen(mainViewModel, dishesViewModel, /*dummyConfiguredLimits,*/ innerPadding)
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
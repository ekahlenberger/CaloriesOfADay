package net.kahlenberger.eberhard.coad.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(val route: String, val title: String="", val icon: ImageVector = Icons.Filled.Menu) {
    Home("home", "Home", Icons.Filled.Home),
    Dishes("dishes", "Dishes", Icons.Filled.Menu),
    AddDishes("addDishes"),
}
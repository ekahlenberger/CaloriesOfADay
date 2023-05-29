package net.kahlenberger.eberhard.coad.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import net.kahlenberger.eberhard.coad.R

enum class Screen(val route: String, val titleResId: Int = 0, val icon: ImageVector = Icons.Filled.Menu) {
    Home("home", R.string.mainNavHome, Icons.Filled.Home),
    Dishes("dishes", R.string.mainNavDishes, Icons.Filled.Menu),
    AddDishes("addDishes"),
}
package net.kahlenberger.eberhard.coad.screens.dishes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.kahlenberger.eberhard.coad.screens.Screen
import net.kahlenberger.eberhard.coad.uidata.Dish
import net.kahlenberger.eberhard.coad.uidata.DishesViewModel

@Composable
fun DishesScreen(viewModel: DishesViewModel, innerPadding: PaddingValues, navController: NavController) {
    val dishes by viewModel.dishes.observeAsState(emptyList())
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Dishes",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(8.dp),
            )

            Box(modifier = Modifier.fillMaxSize())
            {
                LazyColumn {
                    items(dishes) { dish ->
                        DishItem(dish = dish, viewModel = viewModel)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.AddDishes.route) {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Dish")
                }
            }
        }
    }
}

@Composable
fun DishItem(dish: Dish, viewModel: DishesViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = dish.name)
            Text(text = "Direct children: ${dish.childDishes.size}")
            Text(text = "Total calories: ${dish.totalCalories}")
        }

        Column {
            Button(onClick = { /* Edit action */ }) {
                Text(text = "Edit")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.deleteDish(dish) }) {
                Text(text = "Delete")
            }
        }
    }
}
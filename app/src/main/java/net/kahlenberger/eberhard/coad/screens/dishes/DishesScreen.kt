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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.kahlenberger.eberhard.coad.R
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
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.dishesHeadline),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(8.dp),
            )

            Box(modifier = Modifier.fillMaxSize())
            {
                LazyColumn {
                    items(dishes) { dish ->
                        DishItem(dish = dish, onDelete = {d ->  viewModel.deleteDish(d) })
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
                    Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.addDish))
                }
            }
        }
    }
}

@Composable
fun DishItem(dish: Dish, onDelete: (Dish) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dish.name,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${dish.totalCalories} kcal",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.End
            )
            IconButton(onClick = { onDelete(dish) }) {
                Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.deleteDish))
            }
        }
        if (dish.childDishes.size > 1)
        {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = dish.childDishes.sortedBy { it.totalCalories }.joinToString(separator = ", ") { it.name },
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
            }

        }
    }
}
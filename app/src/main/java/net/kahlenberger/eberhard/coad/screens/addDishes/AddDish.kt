package net.kahlenberger.eberhard.coad.screens.dishes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.kahlenberger.eberhard.coad.screens.SingleLineNextTextField
import net.kahlenberger.eberhard.coad.screens.addDishes.AddChildDishDialog
import net.kahlenberger.eberhard.coad.uidata.*

@Composable
fun AddDish(
    viewModel: DishesViewModel,
    navController: NavController
) {
    val dishName = remember { mutableStateOf("") }
    val dishCalories = remember { mutableStateOf("") }
    val selectedChildDishes = remember { mutableStateListOf<Pair<Dish, String>>() }
    val showChildDishDialog = remember { mutableStateOf(false) }
    val dishes by viewModel.dishes.observeAsState(emptyList())

    AddChildDishDialog(
        showChildDishDialog = showChildDishDialog,
        dishes = dishes,
        selectedChildDishes = selectedChildDishes
    )

    Card(modifier = Modifier.fillMaxSize().padding(16.dp))
    {
        Box(modifier = Modifier.fillMaxSize())
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp).
                    padding(start= (0.05f * LocalConfiguration.current.screenWidthDp).dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Add Dish", style = MaterialTheme.typography.h6)
                SingleLineNextTextField(
                    value = dishName.value,
                    onValueChange = { dishName.value = it },
                    label = { Text("Dish Name") },
                    modifier = Modifier.fillMaxWidth(0.9f),
                )
                SingleLineNextTextField(
                    value = dishCalories.value,
                    onValueChange = { dishCalories.value = it },
                    label = { Text("Calories") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                if (!dishes.isEmpty())
                    Column(Modifier.fillMaxSize()) {
                        TextButton(
                            onClick =
                            {
                                showChildDishDialog.value = true
                            }
                        )
                        {
                            Text("Add child dish")
                        }
                        LazyColumn {
                            items(selectedChildDishes) { (dish, quantity) ->
                                Row {
                                    Text(dish.name)
                                    Text(quantity)
                                }
                            }
                        }


                    }


            }
            Row(modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 16.dp)) {

                TextButton(
                    onClick = { navController.navigateUp() }) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    viewModel.addDish(
                        Dish(
                            0,
                            dishName.value,
                            dishCalories.value.toInt(),
                            MeasurementUnit.Grams,
                            "100",
                            selectedChildDishes.map { p -> p.first })
                    )
                    navController.navigateUp()
                }) {
                    Text("Add Dish")
                }
                Spacer(modifier = Modifier.width((0.05f * LocalConfiguration.current.screenWidthDp).dp))
            }
        }
    }
}


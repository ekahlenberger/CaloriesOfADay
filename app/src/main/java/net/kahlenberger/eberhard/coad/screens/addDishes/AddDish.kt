package net.kahlenberger.eberhard.coad.screens.dishes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.kahlenberger.eberhard.coad.screens.SingleLineNextTextField
import net.kahlenberger.eberhard.coad.screens.addDishes.AddChildDishDialog
import net.kahlenberger.eberhard.coad.ui.ComboBox
import net.kahlenberger.eberhard.coad.uidata.Dish
import net.kahlenberger.eberhard.coad.uidata.DishesViewModel
import net.kahlenberger.eberhard.coad.uidata.MeasurementUnit

@Composable
fun AddDish(
    viewModel: DishesViewModel,
    navController: NavController
) {
    val dishName = remember { mutableStateOf("") }
    val dishCalories = remember { mutableStateOf("") }
    val dishAmount = remember { mutableStateOf("") }
    val dishUnit = remember { mutableStateOf(MeasurementUnit.Grams) }  // Default to Grams
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
                Row (
                    modifier = Modifier.fillMaxWidth(0.9f),
                        ) {  // New row for amount and unit
                    SingleLineNextTextField(
                        value = dishAmount.value,
                        onValueChange = { dishAmount.value = it },
                        label = { Text("Amount") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(0.35f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))  // Space between amount and unit
                    ComboBox(
                        items = MeasurementUnit.values().toList(),
                        selectedItem = dishUnit,
                        labelText = "Unit",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
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
                                    Text(" x")
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
                    if (dishCalories.value.toIntOrNull() == null || dishName.value.isEmpty()) return@Button
                    viewModel.addDish(
                        Dish(
                            0,
                            dishName.value,
                            dishCalories.value.toInt(),
                            dishUnit.value,
                            dishAmount.value,
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


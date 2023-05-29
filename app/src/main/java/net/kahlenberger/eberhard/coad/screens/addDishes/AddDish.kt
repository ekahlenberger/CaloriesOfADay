package net.kahlenberger.eberhard.coad.screens.addDishes

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.kahlenberger.eberhard.coad.R
import net.kahlenberger.eberhard.coad.backend.MeasurementUnit
import net.kahlenberger.eberhard.coad.backend.getResourceId
import net.kahlenberger.eberhard.coad.screens.SingleLineNextTextField
import net.kahlenberger.eberhard.coad.ui.ComboBox
import net.kahlenberger.eberhard.coad.uidata.Dish
import net.kahlenberger.eberhard.coad.uidata.DishesViewModel

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

    val localizedUnitNames = mapOf(
        MeasurementUnit.Grams to stringResource(getResourceId(MeasurementUnit.Grams)),
        MeasurementUnit.Kilograms to stringResource(getResourceId(MeasurementUnit.Kilograms)),
        MeasurementUnit.Milliliters to stringResource(getResourceId(MeasurementUnit.Milliliters)),
        MeasurementUnit.Liters to stringResource(getResourceId(MeasurementUnit.Liters)),
        MeasurementUnit.Pieces to stringResource(getResourceId(MeasurementUnit.Pieces)),
        MeasurementUnit.Portions to stringResource(getResourceId(MeasurementUnit.Portions)),
        MeasurementUnit.Teaspoons to stringResource(getResourceId(MeasurementUnit.Teaspoons)),
        MeasurementUnit.Tablespoons to stringResource(getResourceId(MeasurementUnit.Tablespoons)),
        MeasurementUnit.Cups to stringResource(getResourceId(MeasurementUnit.Cups)),
        MeasurementUnit.Pinches to stringResource(getResourceId(MeasurementUnit.Pinches)),
        MeasurementUnit.Dashes to stringResource(getResourceId(MeasurementUnit.Dashes)),
        MeasurementUnit.Pints to stringResource(getResourceId(MeasurementUnit.Pints)),
        MeasurementUnit.Quarts to stringResource(getResourceId(MeasurementUnit.Quarts)),
        MeasurementUnit.Gallons to stringResource(getResourceId(MeasurementUnit.Gallons)),
        MeasurementUnit.Pounds to stringResource(getResourceId(MeasurementUnit.Pounds)),
        MeasurementUnit.Ounces to stringResource(getResourceId(MeasurementUnit.Ounces)),
        MeasurementUnit.Drams to stringResource(getResourceId(MeasurementUnit.Drams)),
    )

    AddChildDishDialog(
        showChildDishDialog = showChildDishDialog,
        dishes = dishes,
        selectedChildDishes = selectedChildDishes
    )

    Card(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
    {
        Box(modifier = Modifier.fillMaxSize())
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp)
                    .padding(start = (0.05f * LocalConfiguration.current.screenWidthDp).dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = stringResource(R.string.addDishHeadline), style = MaterialTheme.typography.h6)
                SingleLineNextTextField(
                    value = dishName.value,
                    onValueChange = { dishName.value = it },
                    label = { Text(stringResource(R.string.addDishNameLabel)) },
                    modifier = Modifier.fillMaxWidth(0.9f),
                )
                SingleLineNextTextField(
                    value = dishCalories.value,
                    onValueChange = { dishCalories.value = it },
                    label = { Text(stringResource(R.string.addDishCaloriesLabel)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                Row (
                    modifier = Modifier.fillMaxWidth(0.9f),
                ) {  // New row for amount and unit
                    SingleLineNextTextField(
                        value = dishAmount.value,
                        onValueChange = { dishAmount.value = it },
                        label = { Text(stringResource(R.string.addDishAmountLabel)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(0.35f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))  // Space between amount and unit
                    ComboBox(
                        items = MeasurementUnit.values().toList(),
                        selectedItem = dishUnit,
                        labelText = stringResource(R.string.addDishUnitLabel),
                        itemToString = { item -> localizedUnitNames[item] ?: "" }
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
                            Text(stringResource(R.string.addDishAddChildDishButton))
                        }
                        LazyColumn {
                            items(selectedChildDishes) { (dish, quantity) ->
                                Row {
                                    Text(dish.name + " - " + quantity + " " + dish.unit)
                                }
                            }
                        }


                    }


            }
            Row(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp)) {

                TextButton(
                    onClick = { navController.navigateUp() }) {
                    Text(stringResource(R.string.cancelButtonText))
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
                            selectedChildDishes.map { p -> p.first.adjusted(p.second) })
                    )
                    navController.navigateUp()
                }) {
                    Text(stringResource(R.string.addDishButtonOk))
                }
                Spacer(modifier = Modifier.width((0.05f * LocalConfiguration.current.screenWidthDp).dp))
            }
        }
    }
}


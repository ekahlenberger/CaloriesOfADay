package net.kahlenberger.eberhard.coad.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import net.kahlenberger.eberhard.coad.R
import net.kahlenberger.eberhard.coad.backend.MeasurementUnit
import net.kahlenberger.eberhard.coad.backend.getResourceId
import net.kahlenberger.eberhard.coad.ui.ComboBox
import net.kahlenberger.eberhard.coad.uidata.ConfiguredLimits
import net.kahlenberger.eberhard.coad.uidata.ConsumedItem
import net.kahlenberger.eberhard.coad.uidata.Dish
import net.kahlenberger.eberhard.coad.uidata.DishesViewModel
import net.kahlenberger.eberhard.coad.uidata.MainViewModel
import java.time.LocalDateTime

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    dishesViewModel: DishesViewModel,
    //configuredLimits: ConfiguredLimits,
    innerPadding: PaddingValues,
) {
    val consumedItems by viewModel.consumedItems.observeAsState(emptyList())
    val consumedSum by viewModel.consumedSum.observeAsState(0)
    val showAddDialog = remember { mutableStateOf(false) }
    val maxCalorieValue by viewModel.maxCalorieValue.observeAsState(0)

    HandleAddDialog(showAddDialog, viewModel, dishesViewModel)
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)) {
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Vertical layout for portrait mode
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.padding(5.dp))
                Box(modifier = Modifier.fillMaxWidth(0.8f)) {
                    CaloriesCard(
                        value = "${consumedSum}",
                        maxCalorieValue = maxCalorieValue,
                        onUpdateMaxCalorieValue = { newMaxCalorieValue ->
                            viewModel.updateMaxCalorieValue(newMaxCalorieValue)
                        },
                        //currentWeight = configuredLimits.startWeight,
                        //startingWeight = configuredLimits.startWeight
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Box( modifier = Modifier.fillMaxSize())
                {
                    ConsumedItemsList(
                        consumedItems,
                        onDelete = { consumedItem -> viewModel.deleteConsumedItem(consumedItem.id) },
                        onIncrement = { consumedItem, index ->
                            val topItem = consumedItems.firstOrNull()
                            if (topItem != null && topItem.name == consumedItem.name && topItem.calories == consumedItem.calories) {
                                // Increment the top item if it matches the clicked item in terms of name and calories
                                viewModel.incrementConsumedItemCount(topItem.id)
                            } else {
                                // Else, just add a new consumed item
                                viewModel.addConsumedItem(consumedItem.copy(id = 0, count = 1, date = LocalDateTime.now()))
                            }
                        }
                    )
                    FloatingActionButton(
                        onClick = { showAddDialog.value = true },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(y = -28.dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Consumed Item")
                    }
                }
            }
        } else {
            // Horizontal layout for landscape mode
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .fillMaxHeight()
                )
                {
                    Spacer(modifier = Modifier.height(32.dp))
                    CaloriesCard(
                        value = "${consumedSum}",
                        maxCalorieValue = maxCalorieValue,
                        onUpdateMaxCalorieValue = { newMaxCalorieValue ->
                            viewModel.updateMaxCalorieValue(newMaxCalorieValue)
                        },
                        //currentWeight = configuredLimits.startWeight,
                        //startingWeight = configuredLimits.startWeight
                    )
                }
                Box( modifier = Modifier.fillMaxSize())
                {
                    ConsumedItemsList(
                        consumedItems,
                        onDelete = { consumedItem -> viewModel.deleteConsumedItem(consumedItem.id) },
                        onIncrement = {consumedItem, index -> if (index == 0) viewModel.incrementConsumedItemCount(consumedItem.id) else viewModel.addConsumedItem(consumedItem.copy(id = 0, count=1,date = LocalDateTime.now())) }
                    )
                    FloatingActionButton(
                        onClick = { showAddDialog.value = true },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = -28.dp)
                            .padding(vertical = 16.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Consumed Item")
                    }
                }
            }
        }
    }
}

@Composable
private fun HandleAddDialog(
    showDialog: MutableState<Boolean>,
    viewModel: MainViewModel,
    dishesViewModel: DishesViewModel
) {
    val dishes by dishesViewModel.dishes.observeAsState(emptyList())
    val selectedDish = remember { mutableStateOf(dishes.firstOrNull()) }
    val itemCalories = remember { mutableStateOf("") }
    val itemQuantity = remember { mutableStateOf("") }

    LaunchedEffect(selectedDish.value) {
        if (selectedDish.value?.totalCalories ?: 0 > 0) {
            itemCalories.value = selectedDish.value!!.totalCalories.toString()
            itemQuantity.value = selectedDish.value!!.basicQuantityInput
        }
    }
    
    LaunchedEffect(itemQuantity.value){
        if (itemQuantity.value.isNotEmpty() && (selectedDish.value?.basicQuantityInput?.toDouble() ?: 0.0) > 0 && selectedDish.value?.totalCalories ?: 0 > 0){
            itemCalories.value = (selectedDish.value!!.totalCalories * itemQuantity.value.toInt() / selectedDish.value?.basicQuantityInput?.toDouble()!!).toInt().toString()
        }
    }
    val quantityBasedUnits = setOf(
        MeasurementUnit.Pieces,
        MeasurementUnit.Portions,
        MeasurementUnit.Teaspoons,
        MeasurementUnit.Tablespoons,
        MeasurementUnit.Cups,
        MeasurementUnit.Pinches,
        MeasurementUnit.Dashes
    )
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(stringResource(R.string.addConsumedItemHeadline)) },
            text = {
                Column {
                    ComboBox(
                        dishes,
                        selectedItem = selectedDish,
                        stringResource(R.string.addConsumedItemDishLabel),
                        itemToString = { it?.name ?: "" },
                        stringToItem = { Dish(0,it,0,MeasurementUnit.Grams,"0") },
                        freeText = true)
                    OutlinedTextField(
                        value = itemQuantity.value,
                        onValueChange = { itemQuantity.value = it },
                        label = {
                            Text(stringResource(R.string.addChildDishQuantityLabel) +
                                    stringResource(selectedDish.value?.unit?.getResourceId() ?: R.string.grams))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = itemCalories.value,
                        onValueChange = { itemCalories.value = it },
                        label = { Text(stringResource(R.string.addConsumedItemCaloriesLabel)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (selectedDish.value == null) return@Button
                        val newConsumedItem = ConsumedItem(
                            name = selectedDish.value!!.name,
                            calories = if (selectedDish.value!!.unit in quantityBasedUnits) selectedDish.value!!.totalCalories else itemCalories.value.toIntOrNull() ?: 0,
                            date = LocalDateTime.now(),
                            count = if (selectedDish.value!!.unit in quantityBasedUnits) itemQuantity.value.toIntOrNull() ?: 1 else 1
                        )
                        viewModel.addConsumedItem(newConsumedItem)
                        showDialog.value = false
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}




package net.kahlenberger.eberhard.coad.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import net.kahlenberger.eberhard.coad.uidata.ConfiguredLimits
import net.kahlenberger.eberhard.coad.uidata.ConsumedItem
import net.kahlenberger.eberhard.coad.uidata.MainViewModel
import java.time.LocalDateTime

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    configuredLimits: ConfiguredLimits,
    innerPadding: PaddingValues,
) {
    val consumedItems by viewModel.consumedItems.observeAsState(emptyList())
    val consumedSum by viewModel.consumedSum.observeAsState(0)
    val showAddDialog = remember { mutableStateOf(false) }
    val itemName = remember { mutableStateOf("") }
    val itemCalories = remember { mutableStateOf("") }
    val maxCalorieValue by viewModel.maxCalorieValue.observeAsState(0)

    HandleAddDialog(showAddDialog, itemName, itemCalories, viewModel)
    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
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
                        currentWeight = configuredLimits.startWeight,
                        startingWeight = configuredLimits.startWeight
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Box( modifier = Modifier.fillMaxSize())
                {
                    ConsumedItemsList(
                        consumedItems,
                        onDelete = { consumedItem -> viewModel.deleteConsumedItem(consumedItem.id) })
                    FloatingActionButton(
                        onClick = { showAddDialog.value = true },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(y = -28.dp).padding(horizontal = 16.dp)
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
                        currentWeight = configuredLimits.startWeight,
                        startingWeight = configuredLimits.startWeight
                    )
                }
                Box( modifier = Modifier.fillMaxSize())
                {
                    ConsumedItemsList(
                        consumedItems,
                        onDelete = { consumedItem -> viewModel.deleteConsumedItem(consumedItem.id) })
                    FloatingActionButton(
                        onClick = { showAddDialog.value = true },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = -28.dp).padding(vertical = 16.dp)
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
    itemName: MutableState<String>,
    itemCalories: MutableState<String>,
    viewModel: MainViewModel
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Add Consumed Item") },
            text = {
                Column {
                    OutlinedTextField(
                        value = itemName.value,
                        onValueChange = { itemName.value = it },
                        label = { Text("Item Name") }
                    )
                    OutlinedTextField(
                        value = itemCalories.value,
                        onValueChange = { itemCalories.value = it },
                        label = { Text("Calories") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newConsumedItem = ConsumedItem(
                            name = itemName.value,
                            calories = itemCalories.value.toIntOrNull() ?: 0,
                            date = LocalDateTime.now()
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




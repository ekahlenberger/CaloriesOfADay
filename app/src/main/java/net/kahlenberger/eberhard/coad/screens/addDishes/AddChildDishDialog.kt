package net.kahlenberger.eberhard.coad.screens.addDishes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import net.kahlenberger.eberhard.coad.uidata.Dish

@Composable
fun AddChildDishDialog(showChildDishDialog: MutableState<Boolean>, dishes: List<Dish>, selectedChildDishes: MutableList<Pair<Dish, String>>){
    val selectedDish = remember { mutableStateOf(dishes.firstOrNull()) }
    val selectedQuantity = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }

    if (!showChildDishDialog.value) return
    AlertDialog(
        onDismissRequest = { showChildDishDialog.value = false },
        title = { Text("Add Child Dish") },
        text = {
            Column (
                horizontalAlignment = Alignment.Start
                    ) {
                Box {
                    TextButton(
                        onClick = { expanded.value = !expanded.value },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(selectedDish.value!!.name)
                    }

                    DropdownMenu(
                        modifier = Modifier.fillMaxWidth(),
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        dishes.forEach { dish ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedDish.value = dish
                                    expanded.value = false
                                },
                                modifier = Modifier.clickable { selectedDish.value = dish }
                            ) {
                                Text(dish.name)
                            }
                        }
                    }
                }
                OutlinedTextField(
                    value = selectedQuantity.value,
                    onValueChange = { selectedQuantity.value = it },
                    label = { Text("Quantity") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedChildDishes.add(selectedDish.value!! to selectedQuantity.value)
                    showChildDishDialog.value = false
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = { showChildDishDialog.value = false }) {
                Text("Cancel")
            }
        }
    )
}
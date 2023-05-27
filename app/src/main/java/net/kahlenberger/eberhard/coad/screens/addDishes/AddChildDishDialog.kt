package net.kahlenberger.eberhard.coad.screens.addDishes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import net.kahlenberger.eberhard.coad.ui.ComboBox
import net.kahlenberger.eberhard.coad.uidata.Dish

@Composable
fun AddChildDishDialog(showChildDishDialog: MutableState<Boolean>, dishes: List<Dish>, selectedChildDishes: MutableList<Pair<Dish, String>>){
    val selectedDish = remember { mutableStateOf(dishes.firstOrNull()) }
    val selectedQuantity = remember { mutableStateOf("") }

    if (!showChildDishDialog.value) return
    AlertDialog(
        onDismissRequest = { showChildDishDialog.value = false },
        title = { Text("Add Child Dish") },
        text = {
            Column (
                horizontalAlignment = Alignment.Start
                    ) {
                ComboBox(items = dishes, selectedItem = selectedDish, labelText = "Dish", itemToString = { dish -> dish?.name ?: ""})
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
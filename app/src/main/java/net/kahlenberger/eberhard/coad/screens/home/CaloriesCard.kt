package net.kahlenberger.eberhard.coad.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.kahlenberger.eberhard.coad.R
import java.lang.Float.min

@Composable
fun CaloriesCard(
    value: String,
    maxCalorieValue: Int,
    onUpdateMaxCalorieValue: (Int) -> Unit,
//    currentWeight: Float,
//    startingWeight: Float
) {
    val consumedCaloriesColor = getColorForConsumedCalories(value.toInt(), maxCalorieValue)
    val isEditing = remember { mutableStateOf(false) }
    val editedMaxCalorieValue = remember { mutableStateOf(maxCalorieValue.toString()) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(R.string.consumedCalories), style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value,
                 style = MaterialTheme.typography.h3.copy(color = consumedCaloriesColor),
                 textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            if (isEditing.value) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 24.dp)
                )
                {
                    OutlinedTextField(
                        value = editedMaxCalorieValue.value,
                        onValueChange = { newValue -> editedMaxCalorieValue.value = newValue },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.width(100.dp),
                        label = { Text("${stringResource(R.string.maxLabel)}:") },
                        isError = editedMaxCalorieValue.value.toIntOrNull() == null
                    )
                    IconButton(
                        onClick = {
                            isEditing.value = false
                            val newMaxValue = editedMaxCalorieValue.value.toIntOrNull()
                            if (newMaxValue != null) {
                                onUpdateMaxCalorieValue(newMaxValue)
                            }
                        },
                        modifier = Modifier
                            .width(24.dp)
                            .padding(8.dp)
                    ) {
                        Icon(Icons.Filled.Done, contentDescription = stringResource(R.string.save))
                    }
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 24.dp)
                )
                {
                    Text(text = "Max: $maxCalorieValue", style = MaterialTheme.typography.body1)
                    IconButton(
                        onClick = {
                            isEditing.value = true
                            editedMaxCalorieValue.value = maxCalorieValue.toString()
                        },
                        modifier = Modifier
                            .width(16.dp)
                            .padding(start = 8.dp)
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit))
                    }
                }
            }
/*
            Spacer(modifier = Modifier.height(16.dp)) // Add a spacer between main info and details

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$currentWeight",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
                Text(text = " / ", style = MaterialTheme.typography.h5) // Add the slash separator
                Text(
                    text = "$startingWeight kg",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
            }
 */
        }
    }
}

fun getColorForConsumedCalories(consumed: Int, limit: Int): Color {
    val green = Color(0xFF6EAF1F)
    val red = Color(0xFFFF0000)
    val minValue = 1200

    return when {
        consumed <= minValue -> {
            val factor = consumed.toFloat() / minValue
            lerp(red, green, factor.coerceIn(0f, 1f))
        }
        consumed <= limit -> green
        else -> {
            val factor = ((consumed - limit) / (limit * 0.1f)).coerceIn(0f, 1f)
            lerp(green, red, min(factor, 1f))
        }
    }
}

package net.kahlenberger.eberhard.coad.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.IntSize

@Composable
fun <T> ComboBox(
    items: List<T>,
    selectedItem: MutableState<T>,
    labelText: String,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var textfieldSize by remember { mutableStateOf(IntSize.Zero) }
    val icon = if (expanded)
        Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.ArrowDropDown

    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current

    Box(modifier) {
        OutlinedTextField(
            value = selectedItem.value.toString(),
            onValueChange = { },
            label = { Text(labelText) },
            trailingIcon = {
                Icon(icon, "combobox open/close icon")
            },
            modifier = textFieldModifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size
                }.onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        expanded = true
                        focusManager.clearFocus()
                    }
                }.pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            expanded = true
                            focusManager.clearFocus()
                        },
                        onTap = {
                            expanded = true
                            focusManager.clearFocus()
                        }
                    )
                },
            readOnly = false,
            interactionSource = interactionSource,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedItem.value = item
                    expanded = false
                }) {
                    Text(text = item.toString())
                }
            }
        }
    }
}



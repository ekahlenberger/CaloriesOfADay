package net.kahlenberger.eberhard.coad.screens

import androidx.compose.foundation.text.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction

@Composable
fun SingleLineNextTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onImeActionPerformed: (ImeAction) -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        maxLines = 1,
        keyboardOptions = keyboardOptions.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { onImeActionPerformed(ImeAction.Next) }),
    )
}

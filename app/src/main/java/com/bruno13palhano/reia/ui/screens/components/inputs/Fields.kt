package com.bruno13palhano.reia.ui.screens.components.inputs

import android.icu.text.DecimalFormat
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import java.util.Locale

@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                overflow = TextOverflow.Ellipsis
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                overflow = TextOverflow.Ellipsis
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { defaultKeyboardAction(ImeAction.Done) })
    )
}

@Composable
fun CommonFloatField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    val decimalFormat = DecimalFormat.getInstance(Locale.getDefault()) as DecimalFormat
    val decimalSeparator = decimalFormat.decimalFormatSymbols.decimalSeparator
    val pattern = remember { Regex("^\\d*\\$decimalSeparator?\\d*\$") }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || newValue.matches(pattern)) {
                onValueChange(newValue)
            }
        },
        label = {
            Text(
                text = label,
                overflow = TextOverflow.Ellipsis
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                overflow = TextOverflow.Ellipsis
            )
        },
        singleLine = true,
        keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Decimal
            ),
        keyboardActions = KeyboardActions(onDone = { defaultKeyboardAction(ImeAction.Done) })
    )
}
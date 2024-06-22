package com.bruno13palhano.reia.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bruno13palhano.reia.R
import com.bruno13palhano.reia.ui.screens.components.inputs.CommonFloatField
import com.bruno13palhano.reia.ui.screens.components.inputs.CommonTextField

@Composable
fun ElectricComponentDialog(
    title: String,
    name: String,
    circuit: String,
    tension: String,
    current: String,
    power: String,
    phase: String,
    type: String,
    onNameChange: (name: String) -> Unit,
    onCircuitChange: (circuit: String) -> Unit,
    onTensionChange: (tension: String) -> Unit,
    onCurrentChange: (current: String) -> Unit,
    onPowerChange: (power: String) -> Unit,
    onPhaseChange: (phase: String) -> Unit,
    onOkClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    ComponentDialog(
        title = title,
        onOkClick = onOkClick,
        onDismissClick = onDismissClick
    ) {
        CommonTextField(
            modifier = Modifier.padding(4.dp),
            value = name,
            label = stringResource(id = R.string.name_label),
            placeholder = stringResource(id = R.string.enter_name_label),
            onValueChange = onNameChange
        )
        CommonTextField(
            modifier = Modifier.padding(4.dp),
            value = circuit,
            label = stringResource(id = R.string.circuit_label),
            placeholder = stringResource(id = R.string.enter_circuit_label),
            onValueChange = onCircuitChange
        )
        CommonFloatField(
            modifier = Modifier.padding(4.dp),
            value = tension,
            label = stringResource(id = R.string.tension_label),
            placeholder = stringResource(id = R.string.enter_tension_label),
            onValueChange = onTensionChange
        )
        CommonFloatField(
            modifier = Modifier.padding(4.dp),
            value = current,
            label = stringResource(id = R.string.current_label),
            placeholder = stringResource(id = R.string.enter_current_label),
            onValueChange = onCurrentChange
        )
        CommonFloatField(
            modifier = Modifier.padding(4.dp),
            value = power,
            label = stringResource(id = R.string.power_label),
            placeholder = stringResource(id = R.string.enter_power_label),
            onValueChange = onPowerChange
        )
        CommonTextField(
            modifier = Modifier.padding(4.dp),
            value = phase,
            label = stringResource(id = R.string.phase_label),
            placeholder = stringResource(id = R.string.enter_phase_label),
            onValueChange = onPhaseChange
        )
    }
}

@Composable
fun BindComponentDialog(
    title: String,
    name: String,
    diameter: String,
    length: String,
    type: String,
    onNameChange: (name: String) -> Unit,
    onDiameterChange: (diameter: String) -> Unit,
    onLengthChange: (length: String) -> Unit,
    onOkClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    ComponentDialog(
        title = title,
        onOkClick = onOkClick,
        onDismissClick = onDismissClick
    ) {
        CommonTextField(
            value = name,
            label = stringResource(id = R.string.name_label),
            placeholder = stringResource(id = R.string.enter_name_label),
            onValueChange = onNameChange
        )
        CommonFloatField(
            value = diameter,
            label = stringResource(id = R.string.diameter_label),
            placeholder = stringResource(id = R.string.enter_diameter_label),
            onValueChange = onDiameterChange
        )
        CommonFloatField(
            value = length,
            label = stringResource(id = R.string.length_label),
            placeholder = stringResource(id = R.string.enter_length_label),
            onValueChange = onLengthChange
        )
    }
}

@Composable
fun BoxComponentDialog(
    title: String,
    name: String,
    height: String,
    width: String,
    depth: String,
    type: String,
    onNameChange: (name: String) -> Unit,
    onHeightChange: (height: String) -> Unit,
    onWidthChange: (width: String) -> Unit,
    onDepthChange: (depth: String) -> Unit,
    onOkClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    ComponentDialog(
        title = title,
        onOkClick = onOkClick,
        onDismissClick = onDismissClick
    ) {
        CommonTextField(
            value = name,
            label = stringResource(id = R.string.name_label),
            placeholder = stringResource(id = R.string.enter_name_label),
            onValueChange = onNameChange
        )
        CommonFloatField(
            value = height,
            label = stringResource(id = R.string.height_label),
            placeholder = stringResource(id = R.string.enter_height_label),
            onValueChange = onHeightChange
        )
        CommonFloatField(
            value = width,
            label = stringResource(id = R.string.width_label),
            placeholder = stringResource(id = R.string.enter_width_label),
            onValueChange = onWidthChange
        )
        CommonFloatField(
            value = depth,
            label = stringResource(id = R.string.depth_label),
            placeholder = stringResource(id = R.string.enter_depth_label),
            onValueChange = onDepthChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentDialog(
    title: String,
    onOkClick: () -> Unit,
    onDismissClick: () -> Unit,
    content: @Composable () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismissClick,
        modifier = Modifier.clip(shape = RoundedCornerShape(5))
    ) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = title
                )

                content()

                Row(modifier = Modifier.padding(8.dp)) {
                    Button(
                        modifier = Modifier.padding(end = 4.dp),
                        onClick = {
                            onDismissClick()
                            onOkClick()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.ok_label))
                    }
                    Button(
                        modifier = Modifier.padding(start = 4.dp),
                        onClick = onDismissClick
                    ) {
                        Text(text = stringResource(id = R.string.cancel_label))
                    }
                }
            }
        }
    }
}
package com.bruno13palhano.reia.ui.screens.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.ShapeLine
import androidx.compose.ui.graphics.vector.ImageVector
import com.bruno13palhano.model.component.types.BindComponentNames
import com.bruno13palhano.model.component.types.BoxComponentNames
import com.bruno13palhano.model.component.types.ElectricComponentNames
import com.bruno13palhano.reia.R

sealed class ComponentOptions(
    @StringRes val resourceId: Int,
    val icon: ImageVector,
    val subComponents: List<SubComponentsOptions>
) {
    data object Electric : ComponentOptions(
        resourceId = R.string.electric_label,
        icon = Icons.Filled.ElectricalServices,
        subComponents = electricComponents
    )

    data object Bind : ComponentOptions(
        resourceId = R.string.bind_label,
        icon = Icons.Filled.ShapeLine,
        subComponents = bindComponents
    )

    data object Box : ComponentOptions(
        resourceId = R.string.box_label,
        icon = Icons.Filled.CheckBoxOutlineBlank,
        subComponents = boxComponents
    )
}

data class SubComponentsOptions(
    val name: String,
    val type: ComponentType,
    @StringRes val resourceId: Int
)

private val electricComponents =
    listOf(
        SubComponentsOptions(
            name = ElectricComponentNames.PLUGIN.value,
            type = ComponentType.Electric,
            resourceId = R.string.plugin_label
        ),
        SubComponentsOptions(
            name = ElectricComponentNames.SOCKET.value,
            type = ComponentType.Electric,
            resourceId = R.string.socket_label
        )
    )

private val bindComponents =
    listOf(
        SubComponentsOptions(
            name = BindComponentNames.WIRE.value,
            type = ComponentType.Bind,
            resourceId = R.string.wire_label
        ),
        SubComponentsOptions(
            name = BindComponentNames.CONDUIT.value,
            type = ComponentType.Bind,
            resourceId = R.string.conduit_label
        )
    )

private val boxComponents =
    listOf(
        SubComponentsOptions(
            name = BoxComponentNames.JUNCTION_BOX.value,
            type = ComponentType.Box,
            resourceId = R.string.junction_box_label
        ),
        SubComponentsOptions(
            name = BoxComponentNames.SWITCHBOARD.value,
            type = ComponentType.Box,
            resourceId = R.string.switchboard_label
        )
    )
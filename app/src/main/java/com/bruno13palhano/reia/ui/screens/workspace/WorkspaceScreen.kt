package com.bruno13palhano.reia.ui.screens.workspace

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bruno13palhano.reia.ui.screens.components.BindComponentDialog
import com.bruno13palhano.reia.ui.screens.components.BoxComponentDialog
import com.bruno13palhano.reia.ui.screens.components.ComponentType
import com.bruno13palhano.reia.ui.screens.components.ComponentsMenu
import com.bruno13palhano.reia.ui.screens.components.ElectricComponentDialog
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun WorkspaceRoute(
    workspaceId: Long,
    onDrawerMenuClick: () -> Unit
) {
    WorkspaceScreen(
        workspaceId = workspaceId,
        onDrawerMenuClick = onDrawerMenuClick
    )
}

@Composable
private fun WorkspaceScreen(
    workspaceId: Long,
    onDrawerMenuClick: () -> Unit,
    viewModel: WorkspaceViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchWorkspace(id = workspaceId)
        viewModel.getElectricComponents(workspaceId = workspaceId)
        viewModel.getBoxComponents(workspaceId = workspaceId)
        viewModel.getBindComponents(workspaceId = workspaceId)
    }

    val workspaceState by viewModel.workspaceState.collectAsStateWithLifecycle()
    val electricComponents by viewModel.electricComponents.collectAsStateWithLifecycle()
    val bindComponents by viewModel.bindComponents.collectAsStateWithLifecycle()
    val boxComponents by viewModel.boxComponents.collectAsStateWithLifecycle()

    val currentElectricComponentState by viewModel.currentElectricComponent.collectAsStateWithLifecycle()
    val currentBindComponentState by viewModel.currentBindComponent.collectAsStateWithLifecycle()
    val currentBoxComponentState by viewModel.currentBoxComponent.collectAsStateWithLifecycle()

    WorkspaceContent(
        workspaceId = workspaceId,
        currentElectricComponentState = currentElectricComponentState,
        currentBindComponentState = currentBindComponentState,
        currentBoxComponentState = currentBoxComponentState,
        onDrawerMenuClick = onDrawerMenuClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkspaceContent(
    workspaceId: Long,
    currentElectricComponentState: ElectricComponentState,
    currentBindComponentState: BindComponentState,
    currentBoxComponentState: BoxComponentState,
    onDrawerMenuClick: () -> Unit
) {
    var showElectricDialog by remember { mutableStateOf(false) }
    var showBindDialog by remember { mutableStateOf(false) }
    var showBoxDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Workspace") },
                navigationIcon = {
                    IconButton(onClick = onDrawerMenuClick) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            ComponentsMenu { subtype, type ->
                when (type) {
                    ComponentType.Electric -> {
                        currentElectricComponentState.updateType(type = subtype)
                        showElectricDialog = true
                    }
                    ComponentType.Bind -> {
                        currentBindComponentState.updateType(type = subtype)
                        showBindDialog = true
                    }
                    ComponentType.Box -> {
                        currentBoxComponentState.updateType(type = subtype)
                        showBoxDialog = true
                    }
                }
            }
        }
    ) {
//        Column(
//            modifier =
//            Modifier
//                .padding(it)
//                .verticalScroll(rememberScrollState())
//                .horizontalScroll(rememberScrollState())
//        ) {
            if (showElectricDialog) {
                ElectricComponentDialog(
                    title = currentBoxComponentState.type,
                    name = currentElectricComponentState.name,
                    circuit = currentElectricComponentState.circuit,
                    tension = currentElectricComponentState.tension,
                    current = currentElectricComponentState.current,
                    power = currentElectricComponentState.power,
                    phase = currentElectricComponentState.phase,
                    type = currentElectricComponentState.type,
                    onNameChange = currentElectricComponentState::updateName,
                    onCircuitChange = currentElectricComponentState::updateCircuit,
                    onTensionChange = currentElectricComponentState::updateTension,
                    onCurrentChange = currentElectricComponentState::updateCurrent,
                    onPowerChange = currentElectricComponentState::updatePower,
                    onPhaseChange = currentElectricComponentState::updatePhase,
                    onOkClick = { currentElectricComponentState.reset() },
                    onDismissClick = {
                        currentElectricComponentState.reset()
                        showElectricDialog = false
                    }
                )
            }

            if (showBindDialog) {
                BindComponentDialog(
                    title = currentBindComponentState.type,
                    name = currentBindComponentState.name,
                    diameter = currentBindComponentState.diameter,
                    length = currentBindComponentState.length,
                    type = currentBindComponentState.type,
                    onNameChange = currentBindComponentState::updateName,
                    onDiameterChange = currentBindComponentState::updateDiameter,
                    onLengthChange = currentBindComponentState::updateLength,
                    onOkClick = { currentBindComponentState.reset() },
                    onDismissClick = {
                        currentBindComponentState.reset()
                        showBindDialog = false
                    }
                )
            }

            if (showBoxDialog) {
                BoxComponentDialog(
                    title = currentBoxComponentState.type,
                    name = currentBoxComponentState.name,
                    height = currentBoxComponentState.height,
                    width = currentBoxComponentState.width,
                    depth = currentBoxComponentState.depth,
                    onNameChange = currentBoxComponentState::updateName,
                    onHeightChange = currentBoxComponentState::updateHeight,
                    onWidthChange = currentBoxComponentState::updateWidth,
                    onDepthChange = currentBoxComponentState::updateDepth,
                    onOkClick = { currentBoxComponentState.reset() },
                    type = currentBoxComponentState.type,
                    onDismissClick = {
                        currentBoxComponentState.reset()
                        showBoxDialog = false
                    }
                )
            }
//        }
//        WorkspacePresentation(
//            modifier = Modifier
//                .padding(it)
//                .verticalScroll(rememberScrollState())
//                .horizontalScroll(rememberScrollState())
//        )
        World(modifier = Modifier
            .fillMaxSize()
            .padding(it))
    }
}

@Composable
fun World(modifier: Modifier = Modifier) {
    var rotation by remember { mutableFloatStateOf(0F) }

    var offset by remember { mutableStateOf(Offset. Zero) }
    var zoom by remember { mutableFloatStateOf(1F) }
    var angle by remember { mutableFloatStateOf(0F) }

    LaunchedEffect(key1 = rotation) {
        delay(10)
        rotation += 1F
        if (rotation > 360F) {
            rotation = 0F
        }
    }

    Canvas(
        modifier =
            modifier
                .size(2000.dp, 2000.dp)
                .pointerInput(Unit) {
                    detectTransformGestures(true) { centroid, pan, gestureZoom, gestureRotate ->
                        val oldScale = zoom
                        val newScale = zoom * gestureZoom

                        offset = (offset + centroid / oldScale)
                            .rotateBy(gestureRotate) - (centroid / newScale + pan / oldScale)
                        zoom = newScale
                        angle += gestureRotate
                    }
                }
                .graphicsLayer {
//                    translationX = -offset.x * zoom
//                    translationY = -offset.y * zoom
//                    scaleX = zoom
//                    scaleY = zoom
//                    transformOrigin = TransformOrigin.Center
                }
    ) {
        drawRect(size = size, color = Color.Gray)
    }

    Canvas(
        modifier = modifier
            .graphicsLayer {
                translationX = -offset.x * zoom
                translationY = -offset.y * zoom
                scaleX = zoom
                scaleY = zoom
                transformOrigin = TransformOrigin.Center
            }
    ) {
        drawLine(
            color = Color.Red,
            start = Offset(size.width / 2F, 0F),
            end = Offset(size.width / 2F, size.height)
        )
    }

    Canvas(
        modifier = modifier
            .graphicsLayer {
                translationX = -offset.x * zoom
                translationY = -offset.y * zoom
                scaleX = zoom
                scaleY = zoom
                transformOrigin = TransformOrigin.Center
            }
    ) {
        drawLine(
            color = Color.Blue,
            start = Offset(0F, size.height / 2F),
            end = Offset(size.width, size.height / 2F)
        )
    }

    Canvas(
        modifier = modifier
            .graphicsLayer {
                translationX = -offset.x * zoom
                translationY = -offset.y * zoom
                scaleX = zoom
                scaleY = zoom
                transformOrigin = TransformOrigin.Center
            }
    ) {
        drawLine(
            color = Color.Green,
            start = Offset(0F, (size.height + size.width) / 2F),
            end = Offset(size.width, (size.height - size.width) / 2)
        )
    }
}

fun Offset.rotateBy(angle: Float): Offset {
    val angleInRadians = angle * PI / 180

    return Offset(
        (x * cos(angleInRadians) - y * sin(angleInRadians)).toFloat(),
        (x * sin(angleInRadians) + y * cos(angleInRadians)).toFloat()
    )
}
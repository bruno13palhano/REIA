package com.bruno13palhano.reia.ui.screens.components.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun ElectricPresentation(

) {

}

@Composable
fun BindPresentation(

) {

}

@Composable
fun BoxPresentation(

) {

}

@Composable
fun WorkspacePresentation(
    modifier: Modifier = Modifier
) {
    val solids = remember {
        mutableStateListOf(
            SolidDrawProperties(
                id = 1L,
                label = "Socket 1",
                center = Offset(100F, 100F),
                radius = 20F,
                color = Color.Red
            ),
            SolidDrawProperties(
                id = 2L,
                label = "Socket 2",
                center = Offset(100F, 300F),
                radius = 20F,
                color = Color.Gray
            ),
            SolidDrawProperties(
                id = 3L,
                label = "Socket 3",
                center = Offset(300F, 100F),
                radius = 20F,
                color = Color.Yellow
            ),
            SolidDrawProperties(
                id = 4L,
                label = "Socket 4",
                center = Offset(400F, 400F),
                radius = 20F,
                color = Color.Blue
            )
        )
    }

    val binds = remember {
        mutableStateListOf(
            BindDrawProperties(
                id = 1L,
                label = "Bind 1",
                start = Offset(100F, 100F),
                end = Offset(100F, 300F),
                color = Color.Red
            ),
            BindDrawProperties(
                id = 2L,
                label = "Bind 2",
                start = Offset(100F, 300F),
                end = Offset(300F, 100F),
                color = Color.Gray
            ),
            BindDrawProperties(
                id = 3L,
                label = "Bind 3",
                start = Offset(300F, 100F),
                end = Offset(400F, 400F),
                color = Color.Yellow
            )
        )
    }

    var touchIndex by remember { mutableIntStateOf(-1) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        touchIndex = -1
                        solids.forEachIndexed { index, solidDrawProperties ->
                            val isTouched =
                                isTouched(
                                    center = solidDrawProperties.center,
                                    touchPosition = offset,
                                    radius = solidDrawProperties.radius
                                )

                            if (isTouched) {
                                touchIndex = index
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        val component = solids.getOrNull(touchIndex)

                        component?.let { drawComponent ->
                            val endPoint = drawComponent.center.plus(dragAmount)

                            solids[touchIndex] =
                                drawComponent.copy(
                                    id = drawComponent.id,
                                    label = drawComponent.label,
                                    center = endPoint,
                                    radius = 50F,
                                    color = drawComponent.color
                                )
                        }
                    },
                    onDragEnd = {
                        val component = solids.getOrNull(touchIndex)

                        component?.let { drawComponent ->
                            solids[touchIndex] =
                                drawComponent.copy(
                                    id = drawComponent.id,
                                    label = drawComponent.label,
                                    center = drawComponent.center,
                                    radius = 20F,
                                    color = drawComponent.color
                                )
                        }
                    }
                )
            }
    ) {
        drawSolids(
            touchIndex = touchIndex,
            drawScope = this,
            solidList = solids
        )
//        solids.forEachIndexed { index, solidDrawProperties ->
//           if (touchIndex != index) {
//               drawCircle(
//                   color = solidDrawProperties.color,
//                   radius = solidDrawProperties.radius,
//                   center = solidDrawProperties.center
//               )
//           }
//        }

        drawBinds(
            touchIndex = touchIndex,
            drawScope = this,
            bindList = binds
        )
//        binds.forEachIndexed { index, bindDrawProperties ->
//            if (touchIndex != index) {
//                drawLine(
//                    color = bindDrawProperties.color,
//                    start = bindDrawProperties.start,
//                    end = bindDrawProperties.end
//                )
//            }
//        }

        if (touchIndex > -1) {
            solids.getOrNull(touchIndex)?.let {
                drawCircle(
                    color = it.color,
                    radius = it.radius,
                    center = it.center
                )
            }
            binds.getOrNull(touchIndex)?.let {
                drawLine(
                    color = it.color,
                    start = it.start,
                    end = it.end
                )
            }
        }
    }
}

private fun drawSolids(
    touchIndex: Int,
    drawScope: DrawScope,
    solidList: List<SolidDrawProperties>
) {
    if (solidList.isNotEmpty()) {
        solidList.forEachIndexed { index, solid ->
            if (touchIndex != index) {
                drawScope.drawCircle(
                    color = solid.color,
                    radius = solid.radius,
                    center = solid.center
                )
            }
        }
    }
}

private fun drawBinds(
    touchIndex: Int,
    drawScope: DrawScope,
    bindList: List<BindDrawProperties>
) {
    if (bindList.isNotEmpty()) {
        bindList.forEachIndexed { index, bind ->
            if (touchIndex != index) {
                drawScope.drawLine(
                    color = bind.color,
                    start = bind.start,
                    end = bind.end
                )
            }
        }
    }
}

private fun isTouched(center: Offset, touchPosition: Offset, radius: Float): Boolean {
    return center.minus(touchPosition).getDistanceSquared() < (radius * radius * 4)
}

data class SolidDrawProperties(
    val id: Long,
    val label: String,
    val center: Offset,
    val radius: Float,
    val color: Color
)

data class BindDrawProperties(
    val id: Long,
    val label: String,
    val start: Offset,
    val end: Offset,
    val color: Color
)
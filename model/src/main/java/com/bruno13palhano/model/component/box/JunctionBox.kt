package com.bruno13palhano.model.component.box

import com.bruno13palhano.model.component.Point

data class JunctionBox(
    override val id: Long,
    override val name: String,
    override val height: Float,
    override val width: Float,
    override val depth: Float,
    override val position: Point
) : Box(
        id = id,
        name = name,
        height = height,
        width = width,
        depth = depth,
        position = position
    )
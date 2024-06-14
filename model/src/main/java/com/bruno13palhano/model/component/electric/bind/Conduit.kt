package com.bruno13palhano.model.component.electric.bind

import com.bruno13palhano.model.component.Point

data class Conduit(
    override val id: Long,
    override val name: String,
    override val startPoint: Point,
    override val endPoint: Point,
    override val diameter: Float,
    override val length: Float,
    val type: String
) : Bind(
        id = id,
        name = name,
        startPoint = startPoint,
        endPoint = endPoint,
        diameter = diameter,
        length = length
    )
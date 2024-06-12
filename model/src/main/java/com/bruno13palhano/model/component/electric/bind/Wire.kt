package com.bruno13palhano.model.component.electric.bind

import com.bruno13palhano.model.component.Component
import com.bruno13palhano.model.component.Point
import com.bruno13palhano.model.component.Color

abstract class Wire(
    override val id: Long,
    override val name: String,
    open val startPoint: Point,
    open val endPoint: Point,
    open val diameter: Float,
    open val length: Float,
    open val color: Color
) : Component(id = id, name = name)
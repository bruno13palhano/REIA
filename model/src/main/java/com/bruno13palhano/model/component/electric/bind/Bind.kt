package com.bruno13palhano.model.component.electric.bind

import com.bruno13palhano.model.component.Component
import com.bruno13palhano.model.component.Point

abstract class Bind(
    override val id: Long,
    override val name: String,
    open val startPoint: Point,
    open val endPoint: Point,
    open val diameter: Float,
    open val length: Float
) : Component(id = id, name = name)
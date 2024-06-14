package com.bruno13palhano.model.component.box

import com.bruno13palhano.model.component.Component
import com.bruno13palhano.model.component.Point

abstract class Box(
    override val id: Long,
    override val name: String,
    open val height: Float,
    open val width: Float,
    open val depth: Float,
    open val position: Point
) : Component(id = id, name = name)
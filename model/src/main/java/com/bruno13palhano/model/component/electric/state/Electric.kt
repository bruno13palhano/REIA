package com.bruno13palhano.model.component.electric.state

import com.bruno13palhano.model.component.Component
import com.bruno13palhano.model.component.Point
import com.bruno13palhano.model.component.electric.Phase

abstract class Electric(
    override val id: Long,
    override val name: String,
    open val circuit: String,
    open val tension: Float,
    open val power: Float,
    open val phase: Phase,
    open val position: Point
) : Component(id = id, name = name)
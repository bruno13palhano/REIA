package com.bruno13palhano.model.component.electric.state

import com.bruno13palhano.model.component.Point
import com.bruno13palhano.model.component.electric.Phase

data class Socket(
    override val id: Long,
    override val name: String,
    override val circuit: String,
    override val tension: Float,
    override val power: Float,
    override val phase: Phase,
    override val position: Point,
    val type: String,
    val inputs: Int,
    val interrupters: Int
) : Electric(
        id = id,
        name = name,
        circuit = circuit,
        tension = tension,
        power = power,
        phase = phase,
        position = position
    )
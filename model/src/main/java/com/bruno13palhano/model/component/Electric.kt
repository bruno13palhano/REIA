package com.bruno13palhano.model.component

data class Electric(
    override val id: Long,
    override val workspaceId: Long,
    override val name: String,
    val circuit: String,
    val tension: Float,
    val current: Float,
    val power: Float,
    val phase: Phase,
    val position: Point,
    val type: String
) : Component(id = id, workspaceId = workspaceId, name = name)
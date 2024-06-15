package com.bruno13palhano.model.component

data class Box(
    override val id: Long,
    override val workspaceId: Long,
    override val name: String,
    val height: Float,
    val width: Float,
    val depth: Float,
    val position: Point
) : Component(id = id, workspaceId = workspaceId, name = name)
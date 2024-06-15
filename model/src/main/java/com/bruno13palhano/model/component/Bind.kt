package com.bruno13palhano.model.component

data class Bind(
    override val id: Long,
    override val workspaceId: Long,
    override val name: String,
    val startPoint: Point,
    val endPoint: Point,
    val diameter: Float,
    val length: Float,
    val type: String
) : Component(id = id, workspaceId = workspaceId, name = name)
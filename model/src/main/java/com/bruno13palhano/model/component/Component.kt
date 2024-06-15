package com.bruno13palhano.model.component

abstract class Component(
    open val id: Long,
    open val workspaceId: Long,
    open val name: String
)
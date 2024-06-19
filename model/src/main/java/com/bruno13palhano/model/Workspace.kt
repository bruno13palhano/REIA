package com.bruno13palhano.model

import com.bruno13palhano.model.component.Component

data class Workspace(
    val id: Long,
    val title: String,
    val components: List<Component>
) {
    companion object {
        fun empty() = Workspace(0, "", emptyList())
    }
}
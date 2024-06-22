package com.bruno13palhano.reia.ui.screens.workspace

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class BoxComponentState {
    var name by mutableStateOf("")
        private set
    var height by mutableStateOf("")
        private set
    var width by mutableStateOf("")
        private set
    var depth by mutableStateOf("")
        private set
    var type by mutableStateOf("")
        private set

    fun updateName(name: String) {
        this.name = name
    }

    fun updateHeight(height: String) {
        this.height = height
    }

    fun updateWidth(width: String) {
        this.width = width
    }

    fun updateDepth(depth: String) {
        this.depth = depth
    }

    fun updateType(type: String) {
        this.type = type
    }

    fun reset() {
        name = ""
        height = ""
        width = ""
        depth = ""
        type = ""
    }
}
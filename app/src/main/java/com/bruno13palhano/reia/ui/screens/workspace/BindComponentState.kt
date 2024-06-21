package com.bruno13palhano.reia.ui.screens.workspace

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class BindComponentState {
    var name by mutableStateOf("")
        private set
    var startPoint by mutableStateOf("")
        private set
    var endPoint by mutableStateOf("")
        private set
    var diameter by mutableStateOf("")
        private set
    var length by mutableStateOf("")
        private set
    var type by mutableStateOf("")
        private set

    fun updateName(name: String) {
        this.name = name
    }

    fun updateStartPoint(startPoint: String) {
        this.startPoint = startPoint
    }

    fun updateEndPoint(endPoint: String) {
        this.endPoint = endPoint
    }

    fun updateDiameter(diameter: String) {
        this.diameter = diameter
    }

    fun updateLength(length: String) {
        this.length = length
    }

    fun updateType(type: String) {
        this.type = type
    }

    fun reset() {
        name = ""
        startPoint = ""
        endPoint = ""
        diameter = ""
        length = ""
        type = ""
    }
}
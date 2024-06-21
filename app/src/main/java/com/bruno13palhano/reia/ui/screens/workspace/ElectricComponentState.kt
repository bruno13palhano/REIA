package com.bruno13palhano.reia.ui.screens.workspace

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.bruno13palhano.reia.ui.screens.components.ComponentType

class ElectricComponentState {
    var name by mutableStateOf("")
        private set
    var circuit by mutableStateOf("")
        private set
    var tension by mutableStateOf("")
        private set
    var current by mutableStateOf("")
        private set
    var power by mutableStateOf("")
        private set
    var phase by mutableStateOf("")
        private set
    val type = ComponentType.Electric.name

    fun updateName(name: String) {
        this.name = name
    }

    fun updateCircuit(circuit: String) {
        this.circuit = circuit
    }

    fun updateTension(tension: String) {
        this.tension = tension
    }

    fun updateCurrent(current: String) {
        this.current = current
    }

    fun updatePower(power: String) {
        this.power = power
    }

    fun updatePhase(phase: String) {
        this.phase = phase
    }

    fun reset() {
        name = ""
        circuit = ""
        tension = ""
        current = ""
        power = ""
        phase = ""
    }
}
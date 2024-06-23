package com.bruno13palhano.reia.ui.screens.workspace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.data.di.WorkspaceDom
import com.bruno13palhano.data.domain.WorkspaceDomain
import com.bruno13palhano.model.Workspace
import com.bruno13palhano.model.component.Bind
import com.bruno13palhano.model.component.Box
import com.bruno13palhano.model.component.Electric
import com.bruno13palhano.model.component.Phase
import com.bruno13palhano.model.component.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkspaceViewModel
    @Inject
    constructor(
        @WorkspaceDom private val workspace: WorkspaceDomain
    ) : ViewModel() {
        private val _workspaceState = MutableStateFlow(Workspace.empty())
        private val _electricComponents = MutableStateFlow(emptyList<Electric>())
        private val _bindComponents = MutableStateFlow(emptyList<Bind>())
        private val _boxComponents = MutableStateFlow(emptyList<Box>())

        private val _currentElectricComponent = MutableStateFlow(ElectricComponentState())
        private val _currentBindComponent = MutableStateFlow(BindComponentState())
        private val _currentBoxComponent = MutableStateFlow(BoxComponentState())

        val workspaceState: StateFlow<Workspace> =
            _workspaceState.stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5_000),
                initialValue = _workspaceState.value
            )

        val electricComponents: StateFlow<List<Electric>> =
            _electricComponents.stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5_000),
                initialValue = _electricComponents.value
            )

        val bindComponents: StateFlow<List<Bind>> =
            _bindComponents.stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5_000),
                initialValue = _bindComponents.value
            )

        val boxComponents: StateFlow<List<Box>> =
            _boxComponents.stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5_000),
                initialValue = _boxComponents.value
            )

        val currentElectricComponent: StateFlow<ElectricComponentState> =
            _currentElectricComponent.stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5_000),
                initialValue = _currentElectricComponent.value
            )

        val currentBindComponent: StateFlow<BindComponentState> =
            _currentBindComponent.stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5_000),
                initialValue = _currentBindComponent.value
            )

        val currentBoxComponent: StateFlow<BoxComponentState> =
            _currentBoxComponent.stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5_000),
                initialValue = _currentBoxComponent.value
            )

        fun fetchWorkspace(id: Long) {
            viewModelScope.launch {
                workspace.getWorkspaceById(id = id).collect {
                    _workspaceState.value = it
                }
            }
        }

        fun getElectricComponents(workspaceId: Long) {
            viewModelScope.launch {
                workspace.getElectricComponents(workspaceId = workspaceId).collect {
                    _electricComponents.value = it
                }
            }
        }

        fun getBoxComponents(workspaceId: Long) {
            viewModelScope.launch {
                workspace.getBoxComponents(workspaceId = workspaceId).collect {
                    _boxComponents.value = it
                }
            }
        }

        fun getBindComponents(workspaceId: Long) {
            viewModelScope.launch {
                workspace.getBindComponents(workspaceId = workspaceId).collect {
                    _bindComponents.value = it
                }
            }
        }

        fun insertElectricComponent() {
            viewModelScope.launch {
                workspace.saveElectricComponent(
                    component =
                        Electric(
                            id = 0L,
                            workspaceId = _workspaceState.value.id,
                            name = _currentElectricComponent.value.name,
                            circuit = _currentElectricComponent.value.circuit,
                            tension = stringToFloat(value = _currentElectricComponent.value.tension),
                            current = stringToFloat(value = _currentElectricComponent.value.current),
                            power = stringToFloat(value = _currentElectricComponent.value.power),
                            phase = Phase.SINGLE_PHASE,
                            position = Point(0.0F, 0.0F),
                            type = _currentElectricComponent.value.type
                        )
                )
            }
        }

        fun insertBindComponent() {
            viewModelScope.launch {
                workspace.saveBindComponent(
                    component =
                        Bind(
                            id = 0L,
                            workspaceId = _workspaceState.value.id,
                            name = _currentBindComponent.value.name,
                            startPoint = Point(x = 0.0F, y = 0.0F),
                            endPoint = Point(x = 0.0F, y = 0.0F),
                            diameter = stringToFloat(value = _currentBindComponent.value.diameter),
                            length = stringToFloat(value = _currentBindComponent.value.length),
                            type = _currentBindComponent.value.type
                        )
                )
            }
        }

        fun insertBoxComponent() {
            viewModelScope.launch {
                workspace.saveBoxComponent(
                    component =
                        Box(
                            id = 0L,
                            workspaceId = _workspaceState.value.id,
                            name = _currentBoxComponent.value.name,
                            height = stringToFloat(value = _currentBoxComponent.value.height),
                            width = stringToFloat(value = _currentBoxComponent.value.width),
                            depth = stringToFloat(value = _currentBoxComponent.value.depth),
                            position = Point(x = 0.0F, y = 0.0F),
                            type = _currentBoxComponent.value.type
                        )
                )
            }
        }

        private fun stringToFloat(value: String) =
            try {
                value.toFloat()
            } catch (e: Exception) {
                0.0F
            }
    }
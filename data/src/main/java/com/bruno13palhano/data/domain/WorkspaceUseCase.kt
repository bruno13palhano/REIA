package com.bruno13palhano.data.domain

import com.bruno13palhano.data.repository.bind.BindRepository
import com.bruno13palhano.data.repository.box.BoxRepository
import com.bruno13palhano.data.repository.electric.ElectricRepository
import com.bruno13palhano.data.repository.workspace.WorkspaceRepository
import com.bruno13palhano.model.Workspace
import com.bruno13palhano.model.component.Bind
import com.bruno13palhano.model.component.Box
import com.bruno13palhano.model.component.Electric
import kotlinx.coroutines.flow.Flow

internal class WorkspaceUseCase(
    private val electricRepository: ElectricRepository,
    private val bindRepository: BindRepository,
    private val boxRepository: BoxRepository,
    private val workspaceRepository: WorkspaceRepository
) : WorkspaceDomain {
    override fun getWorkspaceById(id: Long): Flow<Workspace> {
        return workspaceRepository.findById(id = id)
    }

    override suspend fun saveElectricComponent(component: Electric) {
        electricRepository.saveComponent(component = component)
    }

    override suspend fun deleteElectricComponent(component: Electric) {
        electricRepository.deleteComponent(component = component)
    }

    override fun getElectricComponentById(id: Long): Flow<Electric> {
        return electricRepository.findComponentById(id = id)
    }

    override fun getElectricComponents(): Flow<List<Electric>> {
        return electricRepository.findAllComponents()
    }

    override suspend fun saveBoxComponent(component: Box) {
        boxRepository.saveComponent(component = component)
    }

    override suspend fun deleteBoxComponent(component: Box) {
        boxRepository.deleteComponent(component = component)
    }

    override fun getBoxComponentById(id: Long): Flow<Box> {
        return boxRepository.findComponentById(id = id)
    }

    override fun getBoxComponents(): Flow<List<Box>> {
        return boxRepository.findAllComponents()
    }

    override suspend fun saveBindComponent(component: Bind) {
        bindRepository.saveComponent(component = component)
    }

    override suspend fun deleteBindComponent(component: Bind) {
        bindRepository.deleteComponent(component = component)
    }

    override fun getBindComponentById(id: Long): Flow<Bind> {
        return bindRepository.findComponentById(id = id)
    }

    override fun getBindComponents(): Flow<List<Bind>> {
        return bindRepository.findAllComponents()
    }
}
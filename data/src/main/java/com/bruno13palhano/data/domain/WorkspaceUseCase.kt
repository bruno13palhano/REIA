package com.bruno13palhano.data.domain

import com.bruno13palhano.data.di.BindRep
import com.bruno13palhano.data.di.BoxRep
import com.bruno13palhano.data.di.ElectricRep
import com.bruno13palhano.data.di.WorkspaceRep
import com.bruno13palhano.data.repository.bind.BindRepository
import com.bruno13palhano.data.repository.box.BoxRepository
import com.bruno13palhano.data.repository.electric.ElectricRepository
import com.bruno13palhano.data.repository.workspace.WorkspaceRepository
import com.bruno13palhano.model.Workspace
import com.bruno13palhano.model.component.Bind
import com.bruno13palhano.model.component.Box
import com.bruno13palhano.model.component.Electric
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class WorkspaceUseCase
    @Inject
    constructor(
        @ElectricRep private val electricRepository: ElectricRepository,
        @BindRep private val bindRepository: BindRepository,
        @BoxRep private val boxRepository: BoxRepository,
        @WorkspaceRep private val workspaceRepository: WorkspaceRepository
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

        override fun getElectricComponents(workspaceId: Long): Flow<List<Electric>> {
            return electricRepository.findComponentsByWorkspaceId(workspaceId = workspaceId)
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

        override fun getBoxComponents(workspaceId: Long): Flow<List<Box>> {
            return boxRepository.findComponentsByWorkspaceId(workspaceId = workspaceId)
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

        override fun getBindComponents(workspaceId: Long): Flow<List<Bind>> {
            return bindRepository.findComponentsByWorkspaceId(workspaceId = workspaceId)
        }
    }
package com.bruno13palhano.data.repository.electric

import com.bruno13palhano.data.di.ElectricLocalData
import com.bruno13palhano.data.local.electric.LocalElectric
import com.bruno13palhano.model.component.Electric
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DefaultElectricRepository
    @Inject
    constructor(
        @ElectricLocalData private val localData: LocalElectric
    ) : ElectricRepository {
        override suspend fun saveComponent(component: Electric) {
            if (component.id == 0L) {
                localData.insert(data = component)
            } else {
                localData.update(data = component)
            }
        }

        override suspend fun deleteComponent(component: Electric) {
            localData.delete(id = component.id)
        }

        override fun findComponentById(id: Long): Flow<Electric> {
            return localData.getById(id = id)
        }

        override fun findComponentsByWorkspaceId(workspaceId: Long): Flow<List<Electric>> {
            return localData.getByWorkspaceId(workspaceId = workspaceId)
        }

        override fun findAllComponents(): Flow<List<Electric>> {
            return localData.getAll()
        }
    }
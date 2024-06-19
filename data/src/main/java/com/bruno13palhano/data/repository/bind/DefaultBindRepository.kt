package com.bruno13palhano.data.repository.bind

import com.bruno13palhano.data.di.BindLocalData
import com.bruno13palhano.data.local.bind.LocalBind
import com.bruno13palhano.model.component.Bind
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DefaultBindRepository
    @Inject
    constructor(
        @BindLocalData private val localData: LocalBind
    ) : BindRepository {
        override suspend fun saveComponent(component: Bind) {
            if (component.id == 0L) {
                localData.insert(data = component)
            } else {
                localData.update(data = component)
            }
        }

        override suspend fun deleteComponent(component: Bind) {
            localData.delete(id = component.id)
        }

        override fun findComponentById(id: Long): Flow<Bind> {
            return localData.getById(id = id)
        }

        override fun findComponentsByWorkspaceId(workspaceId: Long): Flow<List<Bind>> {
            return localData.getByWorkspaceId(workspaceId = workspaceId)
        }

        override fun findAllComponents(): Flow<List<Bind>> {
            return localData.getAll()
        }
    }
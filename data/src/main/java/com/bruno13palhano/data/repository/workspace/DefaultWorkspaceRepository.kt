package com.bruno13palhano.data.repository.workspace

import com.bruno13palhano.data.di.WorkspaceLocalData
import com.bruno13palhano.data.local.workspace.LocalWorkspace
import com.bruno13palhano.model.Workspace
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DefaultWorkspaceRepository
    @Inject
    constructor(
        @WorkspaceLocalData private val localData: LocalWorkspace
    ) : WorkspaceRepository {
        override suspend fun insert(workspace: Workspace) {
            localData.insert(data = workspace)
        }

        override suspend fun update(workspace: Workspace) {
            localData.delete(id = workspace.id)
        }

        override suspend fun delete(workspace: Workspace) {
            localData.delete(id = workspace.id)
        }

        override fun findById(id: Long): Flow<Workspace> {
            return localData.getById(id = id)
        }

        override fun findAll(): Flow<List<Workspace>> {
            return localData.getAll()
        }
    }
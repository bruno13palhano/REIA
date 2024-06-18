package com.bruno13palhano.data.repository.workspace

import com.bruno13palhano.model.Workspace
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DefaultWorkspaceRepository
    @Inject
    constructor() : WorkspaceRepository {
        override suspend fun insert(workspace: Workspace) {
            TODO("Not yet implemented")
        }

        override suspend fun update(workspace: Workspace) {
            TODO("Not yet implemented")
        }

        override suspend fun delete(workspace: Workspace) {
            TODO("Not yet implemented")
        }

        override fun findById(id: Long): Flow<Workspace> {
            TODO("Not yet implemented")
        }

        override fun findAll(): Flow<List<Workspace>> {
            TODO("Not yet implemented")
        }
    }
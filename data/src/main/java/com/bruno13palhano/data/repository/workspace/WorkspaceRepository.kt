package com.bruno13palhano.data.repository.workspace

import com.bruno13palhano.model.Workspace
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    suspend fun insert(workspace: Workspace)

    suspend fun update(workspace: Workspace)

    suspend fun delete(workspace: Workspace)

    fun findById(id: Long): Flow<Workspace>

    fun findAll(): Flow<List<Workspace>>
}
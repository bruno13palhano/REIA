package com.bruno13palhano.data.local.workspace

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import cache.WorkspaceQueries
import com.bruno13palhano.data.di.Dispatcher
import com.bruno13palhano.data.di.REIADispatchers.IO
import com.bruno13palhano.model.Workspace
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DefaultLocalWorkspace @Inject constructor(
    private val localQueries: WorkspaceQueries,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : LocalWorkspace {
    override suspend fun insert(data: Workspace) {
        localQueries.insert(
            id = data.id,
            title = data.title,
        )
    }

    override suspend fun update(data: Workspace) {
        localQueries.update(
            title = data.title,
            id = data.id
        )
    }

    override suspend fun delete(id: Long) {
        localQueries.delete(id = id)
    }

    override fun getById(id: Long): Flow<Workspace> {
        return localQueries.getById(id = id, mapper = ::mapToWorkspace)
            .asFlow()
            .mapToOne(ioDispatcher)
    }

    override fun getAll(): Flow<List<Workspace>> {
        return localQueries.getAll(mapper = ::mapToWorkspace)
            .asFlow()
            .mapToList(ioDispatcher)
    }

    private fun mapToWorkspace(
        id: Long,
        title: String,
    ) = Workspace(
        id = id,
        title = title,
        components = listOf()
    )
}
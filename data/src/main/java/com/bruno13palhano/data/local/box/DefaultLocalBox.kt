package com.bruno13palhano.data.local.box

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import cache.BoxQueries
import com.bruno13palhano.data.di.Dispatcher
import com.bruno13palhano.data.di.REIADispatchers.IO
import com.bruno13palhano.model.component.Box
import com.bruno13palhano.model.component.Point
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

internal class DefaultLocalBox
    @Inject
    constructor(
        private val localQueries: BoxQueries,
        @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
    ) : LocalBox {
        override suspend fun insert(data: Box) {
            localQueries.insert(
                workspaceId = data.workspaceId,
                name = data.name,
                height = data.height.toDouble(),
                width = data.width.toDouble(),
                depth = data.depth.toDouble(),
                positionX = data.position.x.toDouble(),
                positionY = data.position.y.toDouble()
            )
        }

        override suspend fun update(data: Box) {
            localQueries.update(
                workspaceId = data.workspaceId,
                name = data.name,
                height = data.height.toDouble(),
                width = data.width.toDouble(),
                depth = data.depth.toDouble(),
                positionX = data.position.x.toDouble(),
                positionY = data.position.y.toDouble(),
                id = data.id
            )
        }

        override suspend fun delete(id: Long) {
            localQueries.delete(id = id)
        }

        override fun getById(id: Long): Flow<Box> {
            return localQueries.getById(id = id, mapper = ::mapToBox)
                .asFlow()
                .mapToOne(ioDispatcher)
                .catch { it.printStackTrace() }
        }

        override fun getByWorkspaceId(workspaceId: Long): Flow<List<Box>> {
            return localQueries.getByWorkspaceId(workspaceId = workspaceId, mapper = ::mapToBox)
                .asFlow()
                .mapToList(ioDispatcher)
                .catch { it.printStackTrace() }
        }

        override fun getAll(): Flow<List<Box>> {
            return localQueries.getAll(mapper = ::mapToBox).asFlow().mapToList(ioDispatcher)
        }

        private fun mapToBox(
            id: Long,
            workspaceId: Long,
            name: String,
            height: Double,
            width: Double,
            depth: Double,
            positionX: Double,
            positionY: Double
        ) = Box(
            id = id,
            workspaceId = workspaceId,
            name = name,
            height = height.toFloat(),
            width = width.toFloat(),
            depth = depth.toFloat(),
            position = Point(x = positionX.toFloat(), y = positionY.toFloat())
        )
    }
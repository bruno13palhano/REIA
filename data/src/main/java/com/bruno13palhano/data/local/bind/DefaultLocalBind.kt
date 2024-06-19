package com.bruno13palhano.data.local.bind

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import cache.BindQueries
import com.bruno13palhano.data.di.Dispatcher
import com.bruno13palhano.data.di.REIADispatchers.IO
import com.bruno13palhano.model.component.Bind
import com.bruno13palhano.model.component.Point
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

internal class DefaultLocalBind
    @Inject
    constructor(
        private val localQueries: BindQueries,
        @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
    ) : LocalBind {
        override suspend fun insert(data: Bind) {
            localQueries.insert(
                workspaceId = data.workspaceId,
                name = data.name,
                startPointX = data.startPoint.x.toDouble(),
                startPointY = data.startPoint.y.toDouble(),
                endPointX = data.endPoint.x.toDouble(),
                endPointY = data.endPoint.y.toDouble(),
                diameter = data.diameter.toDouble(),
                length = data.length.toDouble(),
                type = data.type
            )
        }

        override suspend fun update(data: Bind) {
            localQueries.update(
                workspaceId = data.workspaceId,
                name = data.name,
                startPointX = data.startPoint.x.toDouble(),
                startPointY = data.startPoint.y.toDouble(),
                endPointX = data.endPoint.x.toDouble(),
                endPointY = data.endPoint.y.toDouble(),
                diameter = data.diameter.toDouble(),
                length = data.length.toDouble(),
                type = data.type,
                id = data.id
            )
        }

        override suspend fun delete(id: Long) {
            localQueries.delete(id = id)
        }

        override fun getById(id: Long): Flow<Bind> {
            return localQueries.getById(id = id, mapper = ::mapToBind)
                .asFlow()
                .mapToOne(ioDispatcher)
                .catch { it.printStackTrace() }
        }

        override fun getByWorkspaceId(workspaceId: Long): Flow<List<Bind>> {
            return localQueries.getByWorkspaceId(workspaceId = workspaceId, mapper = ::mapToBind)
                .asFlow()
                .mapToList(ioDispatcher)
                .catch { it.printStackTrace() }
        }

        override fun getAll(): Flow<List<Bind>> {
            return localQueries.getAll(mapper = ::mapToBind)
                .asFlow()
                .mapToList(ioDispatcher)
        }

        private fun mapToBind(
            id: Long,
            workspaceId: Long,
            name: String,
            startPointX: Double,
            startPointY: Double,
            endPointX: Double,
            endPointY: Double,
            diameter: Double,
            length: Double,
            type: String
        ) = Bind(
            id = id,
            workspaceId = workspaceId,
            name = name,
            startPoint = Point(startPointX.toFloat(), startPointY.toFloat()),
            endPoint = Point(endPointX.toFloat(), endPointY.toFloat()),
            diameter = diameter.toFloat(),
            length = length.toFloat(),
            type = type
        )
    }
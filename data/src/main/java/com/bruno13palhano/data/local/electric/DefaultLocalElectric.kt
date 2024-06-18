package com.bruno13palhano.data.local.electric

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import cache.ElectricQueries
import com.bruno13palhano.data.di.Dispatcher
import com.bruno13palhano.data.di.REIADispatchers.IO
import com.bruno13palhano.model.component.Electric
import com.bruno13palhano.model.component.Phase
import com.bruno13palhano.model.component.Point
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DefaultLocalElectric
    @Inject
    constructor(
        private val localQueries: ElectricQueries,
        @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
    ) : LocalElectric {
        override suspend fun insert(data: Electric) {
            localQueries.insert(
                workspaceId = data.workspaceId,
                name = data.name,
                circuit = data.circuit,
                tension = data.tension.toDouble(),
                current = data.current.toDouble(),
                power = data.power.toDouble(),
                phase = data.phase,
                positionX = data.position.x.toDouble(),
                positionY = data.position.y.toDouble(),
                type = data.type
            )
        }

        override suspend fun update(data: Electric) {
            localQueries.update(
                workspaceId = data.workspaceId,
                name = data.name,
                circuit = data.circuit,
                tension = data.tension.toDouble(),
                current = data.current.toDouble(),
                power = data.power.toDouble(),
                phase = data.phase,
                positionX = data.position.x.toDouble(),
                positionY = data.position.y.toDouble(),
                type = data.type,
                id = data.id
            )
        }

        override suspend fun delete(id: Long) {
            localQueries.delete(id = id)
        }

        override fun getById(id: Long): Flow<Electric> {
            return localQueries.getById(id = id, mapper = ::mapToElectric)
                .asFlow()
                .mapToOne(ioDispatcher)
        }

        override fun getAll(): Flow<List<Electric>> {
            return localQueries.getAll(mapper = ::mapToElectric)
                .asFlow()
                .mapToList(ioDispatcher)
        }

        private fun mapToElectric(
            id: Long,
            workspaceId: Long,
            name: String,
            circuit: String,
            tension: Double,
            current: Double,
            power: Double,
            phase: Phase,
            positionX: Double,
            positionY: Double,
            type: String
        ) = Electric(
            id = id,
            workspaceId = workspaceId,
            name = name,
            circuit = circuit,
            tension = tension.toFloat(),
            current = current.toFloat(),
            power = power.toFloat(),
            phase = phase,
            position = Point(x = positionX.toFloat(), y = positionY.toFloat()),
            type = type
        )
    }
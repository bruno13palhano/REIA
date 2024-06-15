package com.bruno13palhano.data.repository.box

import com.bruno13palhano.model.component.Box
import kotlinx.coroutines.flow.Flow

internal class DefaultBoxRepository : BoxRepository {
    override suspend fun saveComponent(component: Box) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteComponent(component: Box) {
        TODO("Not yet implemented")
    }

    override fun findComponentById(id: Long): Flow<Box> {
        TODO("Not yet implemented")
    }

    override fun findAllComponents(): Flow<List<Box>> {
        TODO("Not yet implemented")
    }
}
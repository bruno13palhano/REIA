package com.bruno13palhano.data.repository.bind

import com.bruno13palhano.model.component.Bind
import kotlinx.coroutines.flow.Flow

internal class DefaultBindRepository : BindRepository {
    override suspend fun saveComponent(component: Bind) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteComponent(component: Bind) {
        TODO("Not yet implemented")
    }

    override fun findComponentById(id: Long): Flow<Bind> {
        TODO("Not yet implemented")
    }

    override fun findAllComponents(): Flow<List<Bind>> {
        TODO("Not yet implemented")
    }
}
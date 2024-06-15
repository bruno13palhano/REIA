package com.bruno13palhano.data.repository.electric

import com.bruno13palhano.model.component.Electric
import kotlinx.coroutines.flow.Flow

internal class DefaultElectricRepository : ElectricRepository {
    override suspend fun saveComponent(component: Electric) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteComponent(component: Electric) {
        TODO("Not yet implemented")
    }

    override fun findComponentById(id: Long): Flow<Electric> {
        TODO("Not yet implemented")
    }

    override fun findAllComponents(): Flow<List<Electric>> {
        TODO("Not yet implemented")
    }
}
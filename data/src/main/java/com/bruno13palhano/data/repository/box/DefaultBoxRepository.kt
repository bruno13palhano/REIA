package com.bruno13palhano.data.repository.box

import com.bruno13palhano.data.di.BoxLocalData
import com.bruno13palhano.data.local.box.LocalBox
import com.bruno13palhano.model.component.Box
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DefaultBoxRepository
    @Inject
    constructor(
        @BoxLocalData private val localData: LocalBox
    ) : BoxRepository {
        override suspend fun saveComponent(component: Box) {
            if (component.id == 0L) {
                localData.insert(data = component)
            } else {
                localData.update(data = component)
            }
        }

        override suspend fun deleteComponent(component: Box) {
            localData.delete(id = component.id)
        }

        override fun findComponentById(id: Long): Flow<Box> {
            return localData.getById(id = id)
        }

        override fun findAllComponents(): Flow<List<Box>> {
            return localData.getAll()
        }
    }
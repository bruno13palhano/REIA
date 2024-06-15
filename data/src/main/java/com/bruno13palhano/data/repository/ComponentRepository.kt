package com.bruno13palhano.data.repository

import kotlinx.coroutines.flow.Flow

interface ComponentRepository<T> {
    suspend fun saveComponent(component: T)

    suspend fun deleteComponent(component: T)

    fun findComponentById(id: Long): Flow<T>

    fun findAllComponents(): Flow<List<T>>
}
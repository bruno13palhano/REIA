package com.bruno13palhano.data.local

import kotlinx.coroutines.flow.Flow

internal interface LocalData<T> {
    suspend fun insert(data: T)

    suspend fun update(data: T)

    suspend fun delete(id: Long)

    fun getById(id: Long): Flow<T>

    fun getAll(): Flow<List<T>>
}
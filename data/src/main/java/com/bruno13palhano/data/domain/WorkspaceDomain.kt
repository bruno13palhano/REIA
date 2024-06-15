package com.bruno13palhano.data.domain

import com.bruno13palhano.model.Workspace
import com.bruno13palhano.model.component.Bind
import com.bruno13palhano.model.component.Box
import com.bruno13palhano.model.component.Electric
import kotlinx.coroutines.flow.Flow

interface WorkspaceDomain {
    fun getWorkspaceById(id: Long): Flow<Workspace>

    suspend fun saveElectricComponent(component: Electric)

    suspend fun deleteElectricComponent(component: Electric)

    fun getElectricComponentById(id: Long): Flow<Electric>

    fun getElectricComponents(): Flow<List<Electric>>

    suspend fun saveBoxComponent(component: Box)

    suspend fun deleteBoxComponent(component: Box)

    fun getBoxComponentById(id: Long): Flow<Box>

    fun getBoxComponents(): Flow<List<Box>>

    suspend fun saveBindComponent(component: Bind)

    suspend fun deleteBindComponent(component: Bind)

    fun getBindComponentById(id: Long): Flow<Bind>

    fun getBindComponents(): Flow<List<Bind>>
}
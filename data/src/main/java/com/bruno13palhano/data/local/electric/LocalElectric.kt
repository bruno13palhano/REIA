package com.bruno13palhano.data.local.electric

import com.bruno13palhano.data.local.LocalData
import com.bruno13palhano.model.component.Electric
import kotlinx.coroutines.flow.Flow

internal interface LocalElectric : LocalData<Electric> {
    fun getByWorkspaceId(workspaceId: Long): Flow<List<Electric>>
}
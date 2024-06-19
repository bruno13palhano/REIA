package com.bruno13palhano.data.local.bind

import com.bruno13palhano.data.local.LocalData
import com.bruno13palhano.model.component.Bind
import kotlinx.coroutines.flow.Flow

internal interface LocalBind : LocalData<Bind> {
    fun getByWorkspaceId(workspaceId: Long): Flow<List<Bind>>
}
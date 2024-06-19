package com.bruno13palhano.data.local.box

import com.bruno13palhano.data.local.LocalData
import com.bruno13palhano.model.component.Box
import kotlinx.coroutines.flow.Flow

internal interface LocalBox : LocalData<Box> {
    fun getByWorkspaceId(workspaceId: Long): Flow<List<Box>>
}
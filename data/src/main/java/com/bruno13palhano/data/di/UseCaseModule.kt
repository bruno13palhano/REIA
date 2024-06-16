package com.bruno13palhano.data.di

import com.bruno13palhano.data.domain.WorkspaceDomain
import com.bruno13palhano.data.domain.WorkspaceUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class WorkspaceDom

@InstallIn(SingletonComponent::class)
@Module
internal abstract class UseCaseModule {
    @WorkspaceDom
    @Singleton
    @Binds
    abstract fun bindWorkspaceUseCase(workspace: WorkspaceUseCase): WorkspaceDomain
}
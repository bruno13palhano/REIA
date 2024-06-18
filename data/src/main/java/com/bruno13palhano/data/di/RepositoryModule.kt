package com.bruno13palhano.data.di

import com.bruno13palhano.data.repository.bind.BindRepository
import com.bruno13palhano.data.repository.bind.DefaultBindRepository
import com.bruno13palhano.data.repository.box.BoxRepository
import com.bruno13palhano.data.repository.box.DefaultBoxRepository
import com.bruno13palhano.data.repository.electric.DefaultElectricRepository
import com.bruno13palhano.data.repository.electric.ElectricRepository
import com.bruno13palhano.data.repository.workspace.DefaultWorkspaceRepository
import com.bruno13palhano.data.repository.workspace.WorkspaceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class BindRep

@Qualifier
annotation class BoxRep

@Qualifier
annotation class ElectricRep

@Qualifier
annotation class WorkspaceRep

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepositoryModule {
    @BindRep
    @Singleton
    @Binds
    abstract fun bindBindRepository(bindRepository: DefaultBindRepository): BindRepository

    @BoxRep
    @Singleton
    @Binds
    abstract fun bindBoxRepository(boxRepository: DefaultBoxRepository): BoxRepository

    @ElectricRep
    @Singleton
    @Binds
    abstract fun bindElectricRepository(electricRepository: DefaultElectricRepository): ElectricRepository

    @WorkspaceRep
    @Singleton
    @Binds
    abstract fun bindWorkspaceRepository(workspaceRepository: DefaultWorkspaceRepository): WorkspaceRepository
}
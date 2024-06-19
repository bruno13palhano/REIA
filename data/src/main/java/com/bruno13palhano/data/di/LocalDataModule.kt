package com.bruno13palhano.data.di

import com.bruno13palhano.data.local.bind.DefaultLocalBind
import com.bruno13palhano.data.local.bind.LocalBind
import com.bruno13palhano.data.local.box.DefaultLocalBox
import com.bruno13palhano.data.local.box.LocalBox
import com.bruno13palhano.data.local.electric.DefaultLocalElectric
import com.bruno13palhano.data.local.electric.LocalElectric
import com.bruno13palhano.data.local.workspace.DefaultLocalWorkspace
import com.bruno13palhano.data.local.workspace.LocalWorkspace
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
internal annotation class BindLocalData

@Qualifier
internal annotation class BoxLocalData

@Qualifier
internal annotation class ElectricLocalData

@Qualifier
internal annotation class WorkspaceLocalData

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalDataModule {
    @BindLocalData
    @Singleton
    @Binds
    abstract fun bindBindLocalData(localData: DefaultLocalBind): LocalBind

    @BoxLocalData
    @Singleton
    @Binds
    abstract fun bindBoxLocalData(localData: DefaultLocalBox): LocalBox

    @ElectricLocalData
    @Singleton
    @Binds
    abstract fun bindElectricLocalData(localData: DefaultLocalElectric): LocalElectric

    @WorkspaceLocalData
    @Singleton
    @Binds
    abstract fun bindWorkspaceLocalData(localData: DefaultLocalWorkspace): LocalWorkspace
}
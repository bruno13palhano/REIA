package com.bruno13palhano.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IOScope

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopesModule {
    @ApplicationScope
    @Singleton
    @Provides
    fun providesApplicationScope(
        @Dispatcher(REIADispatchers.DEFAULT) dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    @IOScope
    @Singleton
    @Provides
    fun providesIOScope(
        @Dispatcher(REIADispatchers.IO) dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}
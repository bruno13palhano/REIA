package com.bruno13palhano.data.di

import android.content.Context
import cache.BindQueries
import cache.BoxQueries
import cache.ElectricQueries
import com.bruno13palhano.cache.AppDatabase
import com.bruno13palhano.data.database.DatabaseFactory
import com.bruno13palhano.data.database.DriverFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return DatabaseFactory(
            driverFactory = DriverFactory(context = context)
        ).createDatabase()
    }

    @Provides
    @Singleton
    fun provideElectricQueries(database: AppDatabase): ElectricQueries = database.electricQueries

    @Provides
    @Singleton
    fun provideBoxQueries(database: AppDatabase): BoxQueries = database.boxQueries

    @Provides
    @Singleton
    fun provideBindQueries(database: AppDatabase): BindQueries = database.bindQueries
}
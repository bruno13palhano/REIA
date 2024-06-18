package com.bruno13palhano.data.database

import app.cash.sqldelight.EnumColumnAdapter
import cache.Electric
import com.bruno13palhano.cache.AppDatabase

internal class DatabaseFactory(private val driverFactory: DriverFactory) {
    fun createDatabase(): AppDatabase {
        return AppDatabase(
            driver = driverFactory.createDriver(),
            ElectricAdapter =
                Electric.Adapter(
                    phaseAdapter = EnumColumnAdapter()
                )
        )
    }
}
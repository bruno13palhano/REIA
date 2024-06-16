package com.bruno13palhano.data.database

import com.bruno13palhano.cache.AppDatabase

internal class DatabaseFactory(private val driverFactory: DriverFactory) {
    fun createDriver(): AppDatabase {
        return AppDatabase(
            driver = driverFactory.createDriver()
        )
    }
}
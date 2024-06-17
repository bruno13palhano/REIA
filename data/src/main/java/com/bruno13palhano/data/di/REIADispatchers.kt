package com.bruno13palhano.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: REIADispatchers)

enum class REIADispatchers {
    IO,
    DEFAULT
}
package com.bruno13palhano.physica_engine

abstract class ParticleContactGenerator {
    abstract fun addContact(contacts: Array<ParticleContact>?, limit: Int): Int
}
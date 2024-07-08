package com.bruno13palhano.physica_engine

/**
 * Holds one registered contact generator.
 */
data class ContactGeneratorRegistration(
    var generator: ParticleContactGenerator,
    var next: ContactGeneratorRegistration?
)
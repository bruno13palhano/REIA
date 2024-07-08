package com.bruno13palhano.physica_engine

/**
 * Holds one particle in the linked list of particles.
 */
data class ParticleRegistration(
    var particle: Particle,
    var next: ParticleRegistration?
)

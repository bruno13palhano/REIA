package com.bruno13palhano.physica_engine

data class ParticleForceRegistration(
    val particle: Particle,
    val forceGenerator: ParticleForceGenerator
)
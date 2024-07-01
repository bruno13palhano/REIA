package com.bruno13palhano.physica_engine

interface ParticleForceGenerator {
    fun updateForce(particle: Particle, duration: Double)
}
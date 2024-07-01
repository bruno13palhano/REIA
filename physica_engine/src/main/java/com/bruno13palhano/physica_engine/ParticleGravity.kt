package com.bruno13palhano.physica_engine

class ParticleGravity(val gravity: Vector) : ParticleForceGenerator {
    override fun updateForce(particle: Particle, duration: Double) {
        if (!particle.hasFiniteMass()) return
        particle.addForce(force = gravity.scalarCopy(particle.getParticleMass()))
    }
}
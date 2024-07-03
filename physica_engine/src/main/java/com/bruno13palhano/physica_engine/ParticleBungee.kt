package com.bruno13palhano.physica_engine

class ParticleBungee(
    var other: Particle,
    var springConstant: Double,
    var restLength: Double
) : ParticleForceGenerator {
    override fun updateForce(particle: Particle, duration: Double) {
        // Calculate the vector of the spring.
        val force = Vector()
        particle.getParticlePosition(position = force)
        force.subtract(vector = other.getParticlePosition())

        // Check if the bungee is compressed.
        var magnitude = force.magnitude()
        if (magnitude <= restLength) return

        // Calculate the magnitude of  the force.
        magnitude = springConstant * (restLength - magnitude)

        // Calculate the final force and apply it.
        force.normalize()
        force.scalarMultiplication(value = -magnitude)
        particle.addForce(force = force)
    }
}
package com.bruno13palhano.physica_engine

class ParticleDrag(
    val k1: Double,
    val k2: Double
) : ParticleForceGenerator{
    override fun updateForce(particle: Particle, duration: Double) {
        val force = Vector()
        particle.getParticleVelocity(velocity = force)

        // Calculate the total drag coefficient.
        var dragCoefficient = force.magnitude()
        dragCoefficient = (k1 * dragCoefficient) + (k2 * dragCoefficient * dragCoefficient)

        // Calculate the final force and apply it.
        force.normalize()
        force.scalarMultiplication(value = -dragCoefficient)
        particle.addForce(force = force)
    }
}
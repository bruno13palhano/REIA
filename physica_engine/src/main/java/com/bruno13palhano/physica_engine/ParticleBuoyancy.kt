package com.bruno13palhano.physica_engine

class ParticleBuoyancy(
    var  maxDepth: Double,
    var volume: Double,
    var waterHeight: Double,
    var liquidDensity: Double = 1000.0
) : ParticleForceGenerator {
    override fun updateForce(particle: Particle, duration: Double) {
        // Calculate the submersion depth.
        val depth = particle.getParticlePosition().y

        // Check if we're out of the water.
        if (depth >= waterHeight + maxDepth) return
        val force = Vector()

        // Check if we're at maximum depth.
        if (depth <= waterHeight - maxDepth) {
            force.y = liquidDensity * volume
            particle.addForce(force = force)
        } else {
            force.y = liquidDensity * volume * (depth - maxDepth - waterHeight) / 2 * maxDepth
            particle.addForce(force = force)
        }
    }
}
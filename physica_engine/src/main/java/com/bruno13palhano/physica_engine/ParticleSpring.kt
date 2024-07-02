package com.bruno13palhano.physica_engine

import kotlin.math.abs

class ParticleSpring(
    var other: Particle,
    var springConstant: Double,
    var restLength: Double
) : ParticleForceGenerator {
    override fun updateForce(particle: Particle, duration: Double) {

        // Calculate the vector of the  spring.
        val force = Vector()
        particle.getParticlePosition(position = force)
        force.subtract(vector = other.getParticlePosition())

        //  Calculate the magnitude  of the force.
        var magnitude = force.magnitude()
        magnitude = abs(magnitude - restLength)
        magnitude *= springConstant

        // Calculate the final force and apply it.
        force.normalize()
        force.scalarMultiplication(value = -magnitude)
        particle.addForce(force = force)
    }
}
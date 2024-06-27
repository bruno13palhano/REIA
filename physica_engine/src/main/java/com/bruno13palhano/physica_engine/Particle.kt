package com.bruno13palhano.physica_engine

import kotlin.math.pow

class Particle(
    var position: Vector,
    var velocity: Vector,
    var acceleration: Vector,

    /**
     * Holds the accumulated force to be applied at the next
     * simulation iteration only. This value is zeroed at each
     * integration step.
     */
    var forceAccumulated: Vector,

    /**
     * Holds the amount of damping applied to linear
     * motion. Damping is required to remove energy added
     * through numerical instability in the integrator.
     */
    var damping: Double,

    /**
     * Holds the inverse of the mass of the particle.
     */
    var inverseMass: Double
) {
    /**
     * Integrates the particle forward in time by the given amount.
     * This function uses a Newton-Euler integration method, which is a
     * linear approximation of the correct integral. For this reason it
     * may be inaccurate in some cases.
     */
    fun integrate(duration: Double) {
        if (duration > 0.0) {
            //Update linear position.
            position.addScaledVector(vector = velocity, scale = duration)

            //Work out the acceleration from the force.
            val resultingAcceleration: Vector = acceleration
            resultingAcceleration.addScaledVector(vector = forceAccumulated, scale = duration)

            //Update linear velocity from acceleration.
            velocity.addScaledVector(vector = resultingAcceleration, duration)

            //Impose drag.
            velocity.scalarMultiplication(damping.pow(duration))
        }
    }
}
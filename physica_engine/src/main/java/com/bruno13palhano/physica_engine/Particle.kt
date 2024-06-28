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
        if (inverseMass <= 0.0) return

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

            //Clear the forces.
            clearAccumulator()
        }
    }

    fun setParticleMass(mass: Double) {
        if (mass != 0.0)
            inverseMass = 1.0 / mass
    }

    fun getParticleMass(): Double {
        return if (inverseMass == 0.0) {
            Double.MAX_VALUE
        } else {
            1/inverseMass
        }
    }

    fun setParticleInverseMass(inverseMass: Double) {
        this.inverseMass = inverseMass
    }

    fun getParticleInverseMass(): Double {
        return inverseMass
    }

    fun hasFiniteMass(): Boolean {
        return inverseMass >= 0.0
    }

    fun setParticleDamping(damping: Double) {
        this.damping = damping
    }

    fun getParticleDamping(): Double {
        return damping
    }

    fun setParticlePosition(position: Vector)  {
        this.position = position
    }

    fun setParticlePosition(x: Double, y: Double, z: Double) {
        position.x = x
        position.y = y
        position.z = z
    }

    fun getParticlePosition(position: Vector) {
        position.x = this.position.x
        position.y = this.position.y
        position.z = this.position.z
    }

    fun getParticlePosition(): Vector {
        return position
    }

    fun setParticleVelocity(velocity: Vector) {
        this.velocity = velocity
    }

    fun setParticleVelocity(x: Double, y: Double, z: Double) {
        velocity.x = x
        velocity.y = y
        velocity.z = z
    }

    fun getParticleVelocity(velocity: Vector) {
        velocity.x = this.velocity.x
        velocity.y = this.velocity.y
        velocity.z = this.velocity.z
    }

    fun getParticleVelocity(): Vector {
        return velocity
    }

    fun setParticleAcceleration(acceleration: Vector) {
        this.acceleration = acceleration
    }

    fun setParticleAcceleration(x: Double, y: Double, z: Double) {
        acceleration.x = x
        acceleration.y = y
        acceleration.z = z
    }

    fun getParticleAcceleration(acceleration: Vector) {
        acceleration.x = this.acceleration.x
        acceleration.y = this.acceleration.y
        acceleration.z = this.acceleration.z
    }

    fun getParticleAcceleration(): Vector {
        return acceleration
    }

    fun clearAccumulator() {
        forceAccumulated.clear()
    }

    fun addForce(force: Vector) {
        forceAccumulated.add(force)
    }
}
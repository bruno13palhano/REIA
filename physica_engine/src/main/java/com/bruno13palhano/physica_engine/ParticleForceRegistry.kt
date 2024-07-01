package com.bruno13palhano.physica_engine

class ParticleForceRegistry(val registrations: MutableList<ParticleForceRegistration>) {
    fun add(particle: Particle, forceGenerator: ParticleForceGenerator) {
        val registration = ParticleForceRegistration(
            particle = particle,
            forceGenerator = forceGenerator
        )
        registrations.add(registration)
    }

    fun remove(particle: Particle, forceGenerator: ParticleForceGenerator) {
        val registration = ParticleForceRegistration(
            particle = particle,
            forceGenerator = forceGenerator
        )
        registrations.remove(registration)
    }

    fun clear() {
        registrations.clear()
    }

    fun updateForces(duration: Double) {
        registrations.forEach { forceRegistration ->
            forceRegistration.forceGenerator.updateForce(
                particle = forceRegistration.particle,
                duration =  duration
            )
        }
    }
}
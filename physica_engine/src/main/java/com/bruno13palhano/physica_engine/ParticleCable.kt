package com.bruno13palhano.physica_engine

class ParticleCable(
    particles: Array<Particle>,

    /**
     * Holds the maximum length of the cable.
     */
    var maxLength: Double,

    /**
     * Holds the restitution (bounciness) of the cable.
     */
    var restitution: Double
) : ParticleLink(particles = particles) {
    override fun fillContact(contact: Array<ParticleContact>, limit: Int): Int {
        // Find the length of the cable.
        val length = currentLength()

        // Check whether we're  overextended.
        if (length < maxLength) return 0

        // Otherwise return the contact.
        contact[0].particles[0] = particles[0]
        contact[0].particles[1]  = particles[1]

        // Calculate the normal.
        val  normal = particles[1].getParticlePosition()
            .subtractCopy(vector = particles[0].getParticlePosition())
        normal.normalize()
        contact[0].contactNormal = normal

        contact[0].penetration = length - maxLength
        contact[0].restitution = restitution

        return 1
    }
}
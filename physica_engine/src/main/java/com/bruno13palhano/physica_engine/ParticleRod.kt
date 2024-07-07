package com.bruno13palhano.physica_engine

/**
 * Rods link a pair of particles, generating a contact if they
 * stray too far apart or too close.
 */
class ParticleRod(
    particles: Array<Particle>,

    /**
     * Holds the length of the rod.
     */
    var lenght: Double

) : ParticleLink(particles = particles) {
    override fun fillContact(contact: Array<ParticleContact>, limit: Int): Int {
        // Find the length of the rod.
        val currentLength = currentLength()

        // Check whether we're overextended.
        if (currentLength == lenght) return 0

        // Otherwise return the contact.
        contact[0].particles[0] = particles[0]
        contact[0].particles[1] = particles[1]

        // Calculate the normal.
        val normal = particles[1].getParticlePosition()
            .subtractCopy(vector = particles[0].getParticlePosition())
        normal.normalize()

        // The contact normal depends on whether we're extending
        // or compressing.
        if (currentLength > lenght) {
            contact[0].contactNormal = normal
            contact[0].penetration = currentLength - lenght
        } else {
            contact[0].contactNormal = normal.scalarCopy(value = -1.0)
            contact[0].penetration = lenght - currentLength
        }

        // Always use zero restitution (no bounciness)
        contact[0].restitution = 0.0

        return 1
    }
}
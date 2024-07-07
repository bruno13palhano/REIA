package com.bruno13palhano.physica_engine

/**
 * Links connect two particles together, generating a contact if
 * they violate the constraints of their link. It is used as a
 * base class for cables and rods, and could be used as a base
 * class for springs with a limit to their extension.
 */
abstract class ParticleLink(
    /**
     * Holds the pair of particles that are connected by this link.
     */
    var particles: Array<Particle>
) {
    /**
     * Fills the given contact object with the contact needed
     * to keep the cable from overextending.
     */
    abstract fun fillContact(contact: Array<ParticleContact>, limit: Int): Int

    /**
     * Returns the current length of the cable.
     */
    open fun currentLength(): Double {
        val relativePosition = particles[0].getParticlePosition()
            .subtractCopy(vector = particles[1].getParticlePosition())

        return relativePosition.magnitude()
    }
}
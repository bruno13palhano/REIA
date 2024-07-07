package com.bruno13palhano.physica_engine

/**
 * The contact resolution routine for particle contacts. One
 * resolver instance can be shared for the whole simulation.
 */
class ParticleContactResolver(
    /**
     * Holds the number of iterations
     */
    var iterations: Int
) {
    /**
     * This is a performance tracking value - we keep a record
     * of the number of iterations used.
     */
    private var iterationsUsed: Int = 0

    /**
     * Resolves a set of particle contacts for both penetration
     * and velocity.
     */
    fun resolveContacts(
        contacts: Array<ParticleContact>,
        numContacts: Int,
        duration: Double
    ) {
        iterationsUsed = 0

        while (iterationsUsed < iterations) {
            // Find the contact with the largest closing velocity.
            var max = 0.0
            var maxIndex = 0
            for (i in 0 until numContacts) {
                val sepVel = contacts[i].calculateSeparatingVelocity()
                if (sepVel < max) {
                    max = sepVel
                    maxIndex = i
                }
            }

            // Resolve this contact.
            contacts[maxIndex].resolve(duration = duration)
            iterationsUsed++
        }
    }
}
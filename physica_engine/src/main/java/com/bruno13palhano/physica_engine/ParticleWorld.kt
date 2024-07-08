package com.bruno13palhano.physica_engine

/**
 * Keeps track of a set of particles, and provides the means to
 * update them all.
 */
class ParticleWorld(
    var maxContacts: Int,
    var iterations: Int = 0
) {
    /**
     * Holds the list of registrations.
     */
    var firstParticle: ParticleRegistration? = null

    /**
     * Holds the force generators for the particles in this world.
     */
    var registry: ParticleForceRegistry? = null

    /**
     * Holds the resolver for contacts.
     */
    var resolver: ParticleContactResolver? = null

    /**
     * Holds the list of contact generators.
     */
    var firstContactGenerator: ContactGeneratorRegistration? = null

    /**
     * Holds the list of contacts.
     */
    var contacts: Array<ParticleContact>? = null

    /**
     * Initializes the world for a simulation frame. This clears
     * the force accumulators  for particles in the world. After
     * calling this, the particles can have their forces for this
     * frame added.
     */
    fun startFrame() {
        var registration = firstParticle
        while (registration != null) {
            // Remove all forces from the accumulator.
            registration.particle.clearAccumulator()

            // Get the next registration
            registration = registration.next
        }
    }

    /**
     * Calls each the registered contact generators to report
     * their contacts. Returns the number of generated contacts.
     */
    fun generateContacts(): Int {
        var limit = maxContacts
        var nextContact: MutableList<ParticleContact>? = contacts?.toMutableList()

        var registration = firstContactGenerator
        while (registration != null) {
            val used = registration.generator.addContact(
                contacts = nextContact?.toTypedArray(),
                limit = limit
            )
            limit -= used
            nextContact?.add(used, nextContact[used])

            // We've run out of contacts to fill. This means we're missing
            // contacts.
            if (limit <= 0) break

            registration = registration.next
        }

        // Return the number of contacts used.
        return maxContacts - limit
    }

    /**
     * Integrates all the particles in this world forward in time
     * by the given duration.
     */
    fun integrate(duration: Double) {
        var registration = firstParticle
        while (registration != null) {
            // Integrate all particles.
            registration.particle.integrate(duration = duration)

            // Get the next registration.
            registration = registration.next
        }
    }

    /**
     * Processes all the physics for the particle world.
     */
    fun runPhysics(duration: Double) {
        // First apply the force generators.
        registry?.updateForces(duration = duration)

        // Then integrate the objects.
        integrate(duration = duration)

        // Generate contacts.
        val usedContacts = generateContacts()

        // And process them.
        if (iterations == 0) resolver?.iterations = usedContacts * 2
        resolver?.resolveContacts(
            contacts = contacts!!,
            numContacts = usedContacts,
            duration = duration
        )
    }
}
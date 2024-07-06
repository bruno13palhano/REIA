package com.bruno13palhano.physica_engine

class ParticleContact(
    var particles: Array<Particle?>,
    var restitution: Double,
    var contactNormal: Vector,
    var penetration: Double
) {
    fun resolve(duration: Double) {
        resolveVelocity(duration = duration)
        resolveInterpenetration(duration = duration)
    }

    fun calculateSeparatingVelocity(): Double {
        val relativityVelocity = particles[0]!!.getParticleVelocity()

        particles[1]?.let { particle ->
            relativityVelocity.subtract(particle.getParticlePosition())
        }

        return relativityVelocity.scalarProduct(contactNormal)
    }

    private fun resolveVelocity(duration: Double) {
        // Find the velocity in the direction of the contact.
        val separatingVelocity = calculateSeparatingVelocity()

        /**
         * Check whether it needs to be resolved.
         * the contact is either separating or stationary - there's
         * no impulse required.
         */
        if (separatingVelocity > 0.0) return

        // Calculate the new separating velocity.
        var newSepVelocity = -separatingVelocity * restitution

        // Check the velocity build-up due to acceleration only.
        val accCausedVelocity = particles[0]!!.getParticleAcceleration()
        particles[1]?.let { particle ->
            accCausedVelocity.subtract(particle.getParticleAcceleration())
        }
        val accCausedSepVelocity = accCausedVelocity.scalarProduct(contactNormal.scalarCopy(value = duration))

        // If we've got a closing velocity due to acceleration build-up,
        // remove it from the new separating velocity.
        if (accCausedSepVelocity < 0.0) {
            newSepVelocity += accCausedSepVelocity * accCausedSepVelocity

            // Make sure we haven't removed more than was
            // there to remove
            if (newSepVelocity < 0.0) newSepVelocity = 0.0
        }

        val deltaVelocity = newSepVelocity - separatingVelocity

        /**
         * We apply the change in velocity to each object in proportion to
         * its inverse mass (i.e., those with lower inverse mass [higher
         * actual mass] get less change in velocity.
         */
        var totalInverseMass = particles[0]!!.getParticleInverseMass()
        particles[1]?.let { particle ->
            totalInverseMass += particle.getParticleInverseMass()
        }

        // If all particles have infinite mass, then impulses have no effect.
        if (totalInverseMass <= 0.0) return

        // Calculate the impulse to apply.
        val impulse = deltaVelocity / totalInverseMass

        // Find the amount of impulse per unit of inverse mass.
        val impulsePerInverseMass = contactNormal.scalarCopy(value = impulse)

        /**
         * Apply impulses: they are applied in the direction of the contact,
         * and are proportional to the inverse mass.
         */
        particles[0]!!.setParticleVelocity(
            velocity = particles[0]!!.getParticleVelocity().addCopy(
                vector = impulsePerInverseMass.scalarCopy(particles[0]!!.getParticleInverseMass())
            )
        )

        particles[1]?.let { particle ->
            // Particle 1 goes in the opposite direction.
            particle.setParticleVelocity(
                velocity = particle.getParticleVelocity().addCopy(
                    vector = impulsePerInverseMass.scalarCopy(-particle.getParticleInverseMass())
                )
            )
        }
    }

    fun resolveInterpenetration(duration: Double) {
        // If we don't have any penetration, skip this step.
        if (penetration <= 0.0) return

        /**
         * The movement of each object is based on its inverse mass, so
         * total that.
         */
        var totalInverseMass = particles[0]!!.getParticleInverseMass()
        particles[1]?.let { particle ->
            totalInverseMass += particle.getParticleInverseMass()
        }

        // If all particles have infinite mass, then we do nothing.
        if (totalInverseMass <= 0.0) return

        // Find the amount of penetration resolution per unit of inverse mass.
        val movePerInverseMass = contactNormal.scalarCopy(-penetration / totalInverseMass)

        // Apply the penetration resolution.
        particles[0]!!.setParticlePosition(
            position = particles[0]!!.getParticlePosition().addCopy(
                vector = movePerInverseMass.scalarCopy(value = particles[0]!!.getParticleInverseMass())
            )
        )
        particles[1]?.let { particle ->
            particle.setParticlePosition(
                position = particle.getParticlePosition().addCopy(
                    vector = movePerInverseMass.scalarCopy(value = particle.getParticleInverseMass())
                )
            )
        }
    }
}
package com.bruno13palhano.physica_engine

import kotlin.random.Random

class Firework(
    position: Vector,
    velocity: Vector,
    acceleration: Vector,
    forceAccumulated: Vector,
    damping: Double,
    inverseMass: Double,
    var type: Int,
    var age: Double
) : Particle(
    position,
    velocity,
    acceleration,
    forceAccumulated,
    damping,
    inverseMass
) {
    fun update(duration: Double): Boolean {
        integrate(duration)
        age -= duration

        return (age < 0.0) || (position.y < 0.0)
    }
}

class FireworkRule(
    var type: Int,
    var minAge: Double,
    var maxAge: Double,
    var minVelocity: Vector,
    var maxVelocity: Vector,
    var damping: Double,
    var payloads: Array<Payload>
) {
    private var payloadCount: Int = 0

    fun initRule(payloadCount: Int) {
        this.payloadCount = payloadCount
        payloads[0] = Payload(0, 0)
    }

    fun setParameters(
        type: Int,
        minAge: Double,
        maxAge: Double,
        minVelocity: Vector,
        maxVelocity: Vector,
        damping: Double
    ) {
        this.type = type
        this.minAge = minAge
        this.maxAge = maxAge
        this.minVelocity = minVelocity
        this.maxVelocity =  maxVelocity
        this.damping = damping
    }

    fun create(firework: Firework, parent: Firework? = null): Firework {
        firework.type = type
        firework.age = Random.nextDouble(minAge, maxAge)

        val vel = Vector()

        if (parent != null) {
            firework.setParticlePosition(parent.position)
            vel.add(parent.velocity)
        } else {
            val start = Vector()
            val x = Random.nextInt(3) -1
            start.x = 5.0 * x
            firework.setParticlePosition(start)
        }

        vel.add(
            Vector(
                x = Random.nextDouble(minVelocity.x, maxVelocity.x),
                y = Random.nextDouble(minVelocity.y, maxVelocity.y),
                z = Random.nextDouble(minVelocity.z, maxVelocity.z)
            )
        )

        firework.setParticleVelocity(vel)

        firework.setParticleMass(1.0)
        firework.setParticleDamping(damping)
        firework.setParticleAcceleration(Vector(0.0, 10.0, 0.0))
        firework.clearAccumulator()

        return firework
    }

    data class Payload(
        var type: Int,
        var count: Int
    )  {
        fun set(type: Int, count: Int) {
            this.type = type
            this.count = count
        }
    }
}
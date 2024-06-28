package com.bruno13palhano.physica_engine

import kotlin.math.sqrt

class Vector(
    var x: Double,
    var y: Double,
    var z: Double
) {
    constructor() : this(x = 0.0, y = 0.0, z = 0.0)

    fun invert() {
        x = -x
        y = -y
        z = -z
    }

    fun magnitude(): Double {
        return sqrt(x * x + y * y + z * z)
    }

    fun squareMagnitude(): Double {
        return  x*x + y*y + z*z
    }

    fun normalize() {
        val magnitude = magnitude()
        if (magnitude > 0) {
            x /= magnitude
            y /= magnitude
            z /= magnitude
        }
    }

    fun scalarMultiplication(value: Double) {
        x *= value
        y *= value
        z *= value
    }

    fun scalarCopy(value: Double): Vector {
        return Vector(x * value, y * value, z * value)
    }

    /**
     * Adds the given vector to this.
     */
    fun add(vector: Vector) {
        x += vector.x
        y += vector.y
        z += vector.z
    }

    /**
     * Returns the value of the given vector added to this.
     */
    fun addCopy(vector: Vector): Vector {
        return Vector(x + vector.x, y + vector.y, z + vector.z)
    }

    /**
     * Subtracts the given vector from this.
     */
    fun subtract(vector: Vector) {
        x -= vector.x
        y -= vector.y
        z -= vector.z
    }

    /**
     * Returns the value of the given vector subtracted from this.
     */
    fun subtractCopy(vector: Vector): Vector {
        return Vector(x - vector.x, y - vector.y, z - vector.z)
    }

    /**
     * Add the given vector to this, scaled by the given amount.
     */
    fun addScaledVector(vector: Vector, scale: Double) {
        x += vector.x * scale
        y += vector.y * scale
        z += vector.z * scale
    }

    /**
     * Performs a component-wise product with the given vector and
     * sets this vector to its result.
     */
    fun componentProductUpdate(vector: Vector) {
        x *= vector.x
        y *= vector.y
        z *= vector.z
    }

    /**
     * Calculates and returns a component-wise product of this
     * vector with the given vector.
     */
    fun componentProduct(vector: Vector): Vector {
        return Vector(x * vector.x, y * vector.y, z * vector.z)
    }

    /**
     * Calculates and returns the scalar products of this vector
     * with the given vector.
     */
    fun scalarProduct(vector: Vector): Double {
        return x * vector.x + y * vector.y + z * vector.z
    }

    /**
     * Calculates and returns the vector product of this vector
     * with the given vector
     */
    fun vectorProduct(vector: Vector): Vector {
        return Vector(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        )
    }

    /**
     * Updates this vector to be the vector product of its current
     * value and the given vector.
     */
    fun vectorProductUpdate(vector: Vector) {
        val temp = vectorProduct(vector = vector)

        x = temp.x
        y = temp.y
        z = temp.z
    }

    fun clear() {
        x = 0.0
        y = 0.0
        z = 0.0
    }
}
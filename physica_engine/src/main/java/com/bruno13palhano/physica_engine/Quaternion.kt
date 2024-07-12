package com.bruno13palhano.physica_engine

import kotlin.math.sqrt

/**
 * Holds a three degree of freedom orientation.
 */
class Quaternion(
    /**
     * Holds the real component of the quaternion.
     */
    var r: Double,

    /**
     * Holds the first complex component of the quaternion.
     */
    var i: Double,

    /**
     * Holds the second complex component of the quaternion.
     */
    var j: Double,

    /**
     * Holds the third complex component of the quaternion.
     */
    var k: Double,

    /**
     * Holds the quaternion data in array form.
     */
    val data: Array<Double> = Array(4) { 0.0 }
) {
    fun normalize() {
        var d = r * r + i * i + j * j + k * k

        // Check for zero length quaternion, and use the no-rotation
        // quaternion in that case.
        if (d == 0.0) {
            r = 1.0
            return
        }

        d  = 1.0  / sqrt(d)
        r *= d
        i *= d
        j *= d
        k *= d
    }

    /**
     * Multiplies the quaternion by the given quaternion.
     *
     * @param multiplier The quaternion by which to multiply.
     */
    fun multiply(multiplier: Quaternion) {
        val result = this
        r = result.r * multiplier.r - result.i * multiplier.i - result.j * multiplier.j -
                result.k * multiplier.k
        i = result.r * multiplier.i + result.i * multiplier.r + result.j * multiplier.k -
                result.k * multiplier.j
        j = result.r * multiplier.j + result.j * multiplier.r + result.k * multiplier.i -
                result.i * multiplier.k
        k = result.r * multiplier.k + result.k * multiplier.r + result.i * multiplier.j -
                result.j * multiplier.i
    }

    fun rotateByVector(vector: Vector, scale: Double) {
        val tmp = Quaternion(
            r =  0.0,
            i = vector.x * scale,
            j = vector.y * scale,
            k = vector.z * scale
        )

        this.multiply(tmp)
    }

    /**
     * Adds the given vector to this, scaled by the given amount.
     * This is used to update the orientation quaternion by a rotation
     * and time.
     *
     * @param vector The vector to add.
     *
     * @param scale The amount of  the vector to add.
     */
    fun addScaledVector(vector: Vector, scale: Double) {
        val tmp = Quaternion(
            r = 0.0,
            i = vector.x * scale,
            j = vector.y * scale,
            k = vector.z * scale
        )

        this.multiply(tmp)
        r += tmp.r * .5
        i += tmp.i * .5
        j += tmp.j * .5
        k += tmp.k * .5
    }
}
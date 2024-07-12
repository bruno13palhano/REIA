package com.bruno13palhano.physica_engine.matrix

import com.bruno13palhano.physica_engine.Quaternion
import com.bruno13palhano.physica_engine.Vector

/**
 * Holds an inertia tensor, consisting of a 3x3 row-major matrix.
 * This matrix is not padding to produce an aligned structure, since
 * it is most commonly used with a mass (single real) and two
 * damping coefficients to make the 12-element characteristics array
 * of a rigid body.
 */
class Matrix3(
    val data: Array<Double> = Array(9) { 0.0 }
) {
    /**
     * Transform the given vector by this matrix.
     *
     * @param vector The vector to transform.
     */
    fun transform(vector: Vector): Vector {
        return Vector(
            x = vector.x * data[0] + vector.y * data[1] + vector.z * data[2],
            y = vector.x * data[3] + vector.y * data[4] + vector.z * data[5],
            z = vector.x * data[6] + vector.y * data[7] + vector.z * data[8]
        )
    }

    /**
     * Returns a matrix that is this matrix multiplied by the given
     * other matrix.
     */
    fun multiply(other: Matrix3): Matrix3 {
        return Matrix3(
            data =
                arrayOf(
                    data[0] * other.data[0] + data[1] * other.data[3] + data[2] * other.data[6],
                    data[0] * other.data[1] + data[1] * other.data[4] + data[2] * other.data[7],
                    data[0] * other.data[2] + data[1] * other.data[5] + data[2] * other.data[8],
                    data[3] * other.data[0] + data[4] * other.data[3] + data[5] * other.data[6],
                    data[3] * other.data[1] + data[4] * other.data[4] + data[5] * other.data[7],
                    data[3] * other.data[2] + data[4] * other.data[5] + data[5] * other.data[8],
                    data[6] * other.data[0] + data[7] * other.data[3] + data[8] * other.data[6],
                    data[6] * other.data[1] + data[7] * other.data[4] + data[8] * other.data[7],
                    data[6] * other.data[2] + data[7] * other.data[5] + data[8] * other.data[8]
                )
        )
    }

    /**
     * Sets the matrix to be  the inverse of the given matrix.
     *
     * @param matrix The matrix to invert and use to set this.
     */
    fun setInverse(matrix: Matrix3) {
        val t4 = matrix.data[0] * matrix.data[4]
        val t6 = matrix.data[0] * matrix.data[5]
        val t8 = matrix.data[1] * matrix.data[3]
        val t10 = matrix.data[2] * matrix.data[3]
        val t12 = matrix.data[1] * matrix.data[6]
        val t14 = matrix.data[2] * matrix.data[6]

        // Calculate  the determinant
        val determinant = (t4 * matrix.data[8] - t6 * matrix.data[7] - t8 * matrix.data[8] +
                t10 * matrix.data[7] + t12 * matrix.data[5] - t14 * matrix.data[4])

        // Make sure the determinant is non-zero.
        if (determinant == 0.0) return
        val inverseDet = 1 / determinant

        data[0] = (matrix.data[4] * matrix.data[8] - matrix.data[5] * matrix.data[7]) * inverseDet
        data[1] = -(matrix.data[1] * matrix.data[8] - matrix.data[2] * matrix.data[7]) * inverseDet
        data[2] = (matrix.data[1] * matrix.data[5] - matrix.data[2] * matrix.data[4]) * inverseDet
        data[3] = -(matrix.data[3] * matrix.data[8] - matrix.data[5] * matrix.data[6]) * inverseDet
        data[4] = (matrix.data[0] * matrix.data[8] - t14) * inverseDet
        data[5] = -(t6 - t10) * inverseDet
        data[6] = (matrix.data[3] * matrix.data[7] - matrix.data[4] * matrix.data[6]) * inverseDet
        data[7] = -(matrix.data[0] * matrix.data[7] - t12) * inverseDet
        data[8] = (t4 - t8) * inverseDet
    }

    /**
     * Returns a new matrix containing the inverse  of this  matrix.
     */
    fun inverse(): Matrix3 {
        val result = Matrix3()
        result.setInverse(this)
        return result
    }

    /**
     * Inverts the matrix.
     */
    fun invert() {
        setInverse(this)
    }

    /**
     * Sets the matrix to be the transpose of the given matrix.
     *
     * @param  matrix The matrix to transpose and use to set this.
     */
    fun setTranspose(matrix: Matrix3) {
        data[0] = matrix.data[0]
        data[1] = matrix.data[3]
        data[2] = matrix.data[6]
        data[3] = matrix.data[1]
        data[4] = matrix.data[4]
        data[5] = matrix.data[7]
        data[6] = matrix.data[2]
        data[7] = matrix.data[5]
        data[8] = matrix.data[8]
    }

    /**
     * Returns a new  matrix containing the transpose of this  matrix.
     */
    fun transpose(): Matrix3 {
        val result = Matrix3()
        result.setTranspose(this)
        return result
    }

    /**
     * Sets this matrix to be the rotation matrix corresponding to
     * the given quaternion.
     */
    fun setOrientation(quaternion: Quaternion) {
        data[0] = 1 - (2 * quaternion.j * quaternion.j + 2 * quaternion.k * quaternion.k)
        data[1] = 2 * quaternion.i * quaternion.j + 2 * quaternion.k * quaternion.r
        data[2] = 2 * quaternion.i * quaternion.k - 2 * quaternion.j * quaternion.r
        data[3] = 2 * quaternion.i * quaternion.j - 2 * quaternion.k * quaternion.r
        data[4] = 1 - (2 * quaternion.i * quaternion.i + 2 * quaternion.k * quaternion.k)
        data[5] = 2 * quaternion.j * quaternion.k + 2 * quaternion.i * quaternion.r
        data[6] = 2 * quaternion.i * quaternion.k + 2 * quaternion.j * quaternion.r
        data[7] = 2 * quaternion.j * quaternion.k - 2 * quaternion.i * quaternion.r
        data[8] = 1 - (2 * quaternion.i * quaternion.i + 2 * quaternion.j * quaternion.j)
    }
}
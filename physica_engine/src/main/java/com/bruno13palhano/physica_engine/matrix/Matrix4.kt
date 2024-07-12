package com.bruno13palhano.physica_engine.matrix

import com.bruno13palhano.physica_engine.Quaternion
import com.bruno13palhano.physica_engine.Vector

/**
 * Holds a transform matrix, consisting of a rotation matrix and
 * a position. The matrix has 12 elements; it is assumed that the
 * remaining four are (0,0,0,1), producing a homogenous matrix.
 */
class Matrix4(
    /**
     * Holds the transform matrix data in array form.
     */
    val data: Array<Double> = Array(12) { 0.0 }
) {
    /**
     * Transform the given vector by this matrix.
     *
     * @param vector The vector to transform.
     */
    fun transform(vector: Vector): Vector {
        return Vector(
            x = vector.x * data[0] + vector.y * data[1] + vector.z * data[2] + data[3],
            y = vector.x * data[4] + vector.y * data[5] + vector.z * data[6] + data[7],
            z = vector.x * data[8] + vector.y * data[9] + vector.z * data[10] + data[11]
        )
    }

    /**
     * Returns a matrix that is this matrix multiplied by the given
     * other matrix.
     */
    fun multiply(other: Matrix4): Matrix4 {
        val result = Matrix4()

        result.data[0] = (other.data[0] * data[0]) + (other.data[4] * data[1]) +
                (other.data[8] * data[2])
        result.data[4] = (other.data[0] * data[4]) + (other.data[4] * data[5]) +
                (other.data[8] * data[6])
        result.data[8] = (other.data[0] * data[8]) + (other.data[4] * data[9]) +
                (other.data[8] * data[10])

        result.data[1] = (other.data[1] * data[0]) + (other.data[5] * data[1]) +
                (other.data[9] * data[2])
        result.data[5] = (other.data[1] * data[4]) + (other.data[5] * data[5]) +
                (other.data[9] * data[6])
        result.data[9] = (other.data[1] * data[8]) + (other.data[5] * data[9]) +
                (other.data[9] * data[10])

        result.data[2] = (other.data[2] * data[0]) + (other.data[6] * data[1]) +
                (other.data[10] * data[2])
        result.data[6] = (other.data[2] * data[4]) + (other.data[6] * data[5]) +
                (other.data[10] * data[6])
        result.data[10] = (other.data[2] * data[8]) + (other.data[6] * data[9]) +
                (other.data[10] * data[10])

        result.data[3] = (other.data[3] * data[0]) + (other.data[7] * data[1]) +
                (other.data[11] * data[2]) + data[3]
        result.data[7] = (other.data[3] * data[4]) + (other.data[7] * data[5]) +
                (other.data[11] * data[6]) + data[7]
        result.data[11] = (other.data[3] * data[8]) + (other.data[7] * data[9]) +
                (other.data[11] * data[10]) + data[11]

        return result
    }

    /**
     * Returns the determinant of the matrix.
     */
    fun getDeterminant(): Double {
        return data[8] * data[5] * data[2] +
               data[4] * data[9] * data[2] +
               data[8] * data[1] * data[6] -
               data[0] * data[9] * data[6] -
               data[4] * data[1] * data[10] -
               data[0] * data[5] * data[10]

    }

    /**
     * Sets the matrix to be the inverse of the given matrix.
     *
     * @param matrix The matrix to invert and use to set this.
     */
    fun setInverse(matrix: Matrix4) {
        // Make sure the determinant is non-zero.
        val determinant = getDeterminant()
        if (determinant == 0.0) return
        val inverseDet = 1 / determinant

        data[0] = (-matrix.data[9] * matrix.data[6] + matrix.data[5] * matrix.data[10]) * inverseDet
        data[4] = (matrix.data[8] * matrix.data[6] - matrix.data[4] * matrix.data[10]) * inverseDet
        data[8] = (-matrix.data[8] * matrix.data[5] + matrix.data[4] * matrix.data[9] *
                matrix.data[15]) * inverseDet

        data[1] = (matrix.data[9] * matrix.data[2] - matrix.data[1] * matrix.data[10]) * inverseDet
        data[5] = (-matrix.data[8] * matrix.data[2] + matrix.data[0] * matrix.data[10]) * inverseDet
        data[9] = (matrix.data[8] * matrix.data[1] - matrix.data[0] * matrix.data[9] *
                matrix.data[15]) * inverseDet

        data[2] = (-matrix.data[5] * matrix.data[2] + matrix.data[1] * matrix.data[6] *
                matrix.data[15]) * inverseDet
        data[6] = (matrix.data[4] * matrix.data[2] - matrix.data[0] * matrix.data[6] *
                matrix.data[15]) * inverseDet
        data[10] = (-matrix.data[4] * matrix.data[1] + matrix.data[0] * matrix.data[5] *
                matrix.data[15]) * inverseDet

        data[3] =
            (
                    matrix.data[9] * matrix.data[6] * matrix.data[3]
                    - matrix.data[5] * matrix.data[10] * matrix.data[3]
                    - matrix.data[9] * matrix.data[2] * matrix.data[7]
                    + matrix.data[1] * matrix.data[10] * matrix.data[7]
                    + matrix.data[5] * matrix.data[2] * matrix.data[11]
                    - matrix.data[1] * matrix.data[6] * matrix.data[11]
            ) * inverseDet

        data[7] =
            (
                    - matrix.data[8] * matrix.data[6] * matrix.data[3]
                    + matrix.data[4] * matrix.data[10] * matrix.data[3]
                    + matrix.data[8] * matrix.data[2] * matrix.data[7]
                    - matrix.data[0] * matrix.data[10] * matrix.data[7]
                    + matrix.data[0] * matrix.data[6] * matrix.data[11]
            ) * inverseDet

        data[11] =
            (
                    matrix.data[8] * matrix.data[5] * matrix.data[3]
                    - matrix.data[4] * matrix.data[9] * matrix.data[3]
                    - matrix.data[8] * matrix.data[1] * matrix.data[7]
                    + matrix.data[0] * matrix.data[9] * matrix.data[7]
                    + matrix.data[4] * matrix.data[1] * matrix.data[11]
                    - matrix.data[0] * matrix.data[5] * matrix.data[11]
            ) * inverseDet
    }

    /**
     * Returns a new matrix containing the inverse of this matrix.
     */
    fun inverse(): Matrix4 {
        val result = Matrix4()
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
     * Sets this matrix to be the rotation matrix corresponding to
     * the given quaternion.
     */
    fun setOrientation(quaternion: Quaternion, position: Vector) {
        data[0] = 1 - (2 * quaternion.j * quaternion.j + 2 * quaternion.k * quaternion.k)
        data[1] = 2 * quaternion.i * quaternion.j + 2 * quaternion.k * quaternion.r
        data[2] = 2 * quaternion.i * quaternion.k - 2 * quaternion.j * quaternion.r
        data[3] = position.x

        data[4] = 2 * quaternion.i * quaternion.j - 2 * quaternion.k * quaternion.r
        data[5] = 1 - (2 * quaternion.i * quaternion.i + 2 * quaternion.k * quaternion.k)
        data[6] = 2 * quaternion.j * quaternion.k + quaternion.i * quaternion.r
        data[7] = position.y

        data[8] = 2 * quaternion.i * quaternion.k + 2 * quaternion.j * quaternion.r
        data[9] = 2 * quaternion.j * quaternion.k - 2 * quaternion.i * quaternion.r
        data[10] = 1 - (2 * quaternion.i * quaternion.i + 2 * quaternion.j * quaternion.j)
        data[11] = position.z
    }

    /**
     * Transform the given  vector by the transformational inverse
     * of  this matrix.
     */
    fun transformInverse(vector: Vector): Vector {
        val tmp = vector
        tmp.x -= data[3]
        tmp.y -= data[7]
        tmp.z -= data[11]

        return Vector(
            x = tmp.x * data[0] + tmp.y * data[4] + tmp.z * data[8],
            y = tmp.x * data[1] + tmp.y * data[5] + tmp.z * data[9],
            z = tmp.x * data[2] + tmp.y * data[6] + tmp.z * data[10]
        )
    }

    /**
     * Transform  the given  direction  vector by this matrix.
     */
    fun transformDirection(vector: Vector): Vector {
        return Vector(
            x = vector.x * data[0] + vector.y * data[1] + vector.z * data[2],
            y = vector.x * data[4] + vector.y * data[5] + vector.z * data[6],
            z = vector.x * data[8] + vector.y * data[9] + vector.z * data[10]
        )
    }

    /**
     * Transform the given direction vector by the
     * transformational inverse of this matrix.
     */
    fun transformInversionDirection(vector: Vector): Vector {
        return Vector(
            x = vector.x * data[0] + vector.y * data[4] + vector.z * data[8],
            y = vector.x * data[1] + vector.y * data[5] + vector.z * data[9],
            z = vector.x * data[2] + vector.y * data[6] + vector.z * data[10]
        )
    }
}
package com.bruno13palhano.physica_engine.rigid

import com.bruno13palhano.physica_engine.Quaternion
import com.bruno13palhano.physica_engine.Vector
import com.bruno13palhano.physica_engine.matrix.Matrix3
import com.bruno13palhano.physica_engine.matrix.Matrix4
import kotlin.math.pow

/**
 * A rigid body is the basic simulation object in the physics core.
 */
class RigidBody(
    /**
     * Holds the inverse of the mass of the rigid body.
     */
    var inverseMass: Double,

    /**
     * Holds the linear position of the rigid body in world space.
     */
    var position: Vector,

    /**
     * Holds the angular orientation of the rigid body in world space.
     */
    var orientation: Quaternion,

    /**
     * Holds the linear velocity of the rigid body in world space.
     */
    var velocity: Vector,

    /**
     * Holds the angular velocity, or rotation, or the rigid body
     * in world space.
     */
    var rotation: Vector,

    /**
     * Holds the inverse inertia tensor of the body in world
     * space. The inverse inertia tensor member is specified in
     * the body's local space.
     */
    var inverseInertialTensorWorld: Matrix3,

    /**
     * Holds a transform, matrix for converting body space into world
     * space and vice versa.
     */
    var transformMatrix: Matrix4,

    /**
     * Holds the inverse of the body's inertia tensor. The inertia
     * tensor provided must not be degenerate (that would mean
     * the body had zero inertia for spinning along one axis).
     * As long as the tensor is finite, it will be invertible.
     *
     * The inertia tensor, unlike the other variables that define
     * a rigid body, is given in  body space.
     */
    var inverseInertiaTensor: Matrix3,

    /**
     * Holds the accumulated force to be applied at the next
     * integration step.
     *
     */
    var forceAccumulated: Vector,

    /**
     * Holds the accumulated torque to be applied at the next
     * integration step.
     */
    var torqueAccumulated: Vector,

    /**
     * Holds the amount of damping applied to linear
     * motion. Damping is required to remove energy added
     * through numerical instability in the integrator.
     */
    var linearDamping: Double,

    /**
     * Holds the amount of damping applied to angular
     * motion. Damping is required to remove energy added
     * through numerical instability in the integrator.
     */
    var angularDamping: Double,

    /**
     * Holds the acceleration of the rigid body. This value
     * can be used to set acceleration due to gravity (its primary
     * use), or any other constant acceleration.
     */
    var acceleration: Vector,

    /**
     * Holds the linear acceleration of the rigid body, for the
     * previous frame.
     */
    var lastFrameAcceleration: Vector
) {

    /**
     * Calculates internal data from state data. This should be called
     * after the body's state is altered directly (it is called
     * automatically during integration). If you change the body's
     * state and the intend to integrate before querying any data
     * (such as the transform matrix), then you can omit this step.
     */
    fun calculateDerivedData() {

        // Calculate the transform matrix for the body.
        calculateTransformMatrix(
            transformMatrix = transformMatrix,
            position = position,
            orientation = orientation
        )

        // Calculate the inertialTensor in world space.
        transformInertiaTensor(
            iiWorld = inverseInertiaTensor,
            orientation = orientation,
            iiBody = inverseInertiaTensor,
            transformMatrix = transformMatrix
        )
    }

    private fun calculateTransformMatrix(
        transformMatrix: Matrix4,
        position: Vector,
        orientation: Quaternion
    ) {
        transformMatrix.data[0] = 1 - 2 * orientation.j * orientation.j - 2 * orientation.k
        transformMatrix.data[1] = 2 * orientation.i * orientation.j -
                2 * orientation.r * orientation.k
        transformMatrix.data[2] = 2 * orientation.i * orientation.k +
                2 * orientation.r * orientation.j
        transformMatrix.data[3] = position.x

        transformMatrix.data[4] = 2 * orientation.i * orientation.j +
                2 * orientation.r * orientation.k
        transformMatrix.data[5] = 1 - 2 * orientation.i * orientation.i -
                2 * orientation.k * orientation.k
        transformMatrix.data[6] = 2 * orientation.j * orientation.k -
                2 * orientation.r * orientation.i
        transformMatrix.data[7] = position.y

        transformMatrix.data[8] = 2 * orientation.i * orientation.k -
                2 * orientation.r * orientation.j
        transformMatrix.data[9] = 2 * orientation.j * orientation.k +
                2 * orientation.r * orientation.i
        transformMatrix.data[10] = 1 - 2 * orientation.i * orientation.i -
                2 * orientation.j * orientation.j
        transformMatrix.data[11] = position.z
    }

    fun setInertialTensor(inertiaTensor: Matrix3) {
        inverseInertiaTensor.setInverse(inertiaTensor)
    }

    /**
     * Internal function to do an inertia tensor transform by a quaternion.
     * Note tha the implementation of this function was created by an
     * automated code generator and optimizer.
     */
    private fun transformInertiaTensor(
        iiWorld: Matrix3,
        orientation: Quaternion,
        iiBody: Matrix3,
        transformMatrix: Matrix4
    ) {
        val t4 = transformMatrix.data[0] * iiBody.data[0] +
                transformMatrix.data[1] * iiBody.data[3] +
                transformMatrix.data[2] * iiBody.data[6]
        val t9 = transformMatrix.data[0] * iiBody.data[1] +
                transformMatrix.data[1] * iiBody.data[4] +
                transformMatrix.data[2] * iiBody.data[7]
        val t14 = transformMatrix.data[0] * iiBody.data[2] +
                transformMatrix.data[1] * iiBody.data[5] +
                transformMatrix.data[2] * iiBody.data[8]
        val t28 = transformMatrix.data[4] * iiBody.data[0] +
                transformMatrix.data[5] * iiBody.data[3] +
                transformMatrix.data[6] * iiBody.data[6]
        val  t33 = transformMatrix.data[4] * iiBody.data[1] +
                transformMatrix.data[5] * iiBody.data[4] +
                transformMatrix.data[6] * iiBody.data[7]
        val t38 = transformMatrix.data[4] * iiBody.data[2] +
                transformMatrix.data[5] * iiBody.data[5] +
                transformMatrix.data[6] * iiBody.data[8]
        val t52 = transformMatrix.data[8] * iiBody.data[0] +
                transformMatrix.data[9] * iiBody.data[3] +
                transformMatrix.data[10] * iiBody.data[6]
        val t57 = transformMatrix.data[8] * iiBody.data[1] +
                transformMatrix.data[9] * iiBody.data[4] +
                transformMatrix.data[10] * iiBody.data[7]
        val t62 = transformMatrix.data[8] * iiBody.data[2] +
                transformMatrix.data[9] * iiBody.data[5] +
                transformMatrix.data[10] * iiBody.data[8]

        iiWorld.data[0] = t4 * transformMatrix.data[0] +
                t9 * transformMatrix.data[1] +
                t14 * transformMatrix.data[2]
        iiWorld.data[1] = t4 * transformMatrix.data[4] +
                t9 * transformMatrix.data[5] +
                t14 * transformMatrix.data[6]
        iiWorld.data[2] = t4 * transformMatrix.data[8] +
                t9 * transformMatrix.data[9] +
                t14 * transformMatrix.data[10]
        iiWorld.data[3] = t28 * transformMatrix.data[0] +
                t33 * transformMatrix.data[1] +
                t38 * transformMatrix.data[2]
        iiWorld.data[4] = t28 * transformMatrix.data[4] +
                t33 * transformMatrix.data[5] +
                t38 * transformMatrix.data[6]
        iiWorld.data[5] = t28 * transformMatrix.data[8] +
                t33 * transformMatrix.data[9] +
                t38 * transformMatrix.data[10]
        iiWorld.data[6] = t52 * transformMatrix.data[0] +
                t57 * transformMatrix.data[1] +
                t62 * transformMatrix.data[2]
        iiWorld.data[7] = t52 * transformMatrix.data[4] +
                t57 * transformMatrix.data[5] +
                t62 * transformMatrix.data[6]
        iiWorld.data[8] = t52 * transformMatrix.data[8] +
                t57 * transformMatrix.data[9] +
                t62 * transformMatrix.data[10]
    }

    /**
     * Adds the given force to the center of mass of the rigid body.
     * The force is expressed in world coordinates.
     *
     * @param force The force to apply.
     */
    fun addForce(force: Vector) {
        forceAccumulated.addCopy(vector = force)
    }

    fun integrate(duration: Double) {
        // Calculate linear acceleration from force inputs.
        lastFrameAcceleration = acceleration
        lastFrameAcceleration.addScaledVector(
            vector = forceAccumulated,
            scale = inverseMass
        )

        // Calculate angular acceleration from torque inputs.
        val angularAcceleration = inverseInertialTensorWorld.transform(vector = torqueAccumulated)

        // Adjust velocities
        // Update linear velocity from both acceleration and impulse.
        velocity.addScaledVector(vector = lastFrameAcceleration, scale = duration)

        // Update angular velocity from both acceleration and impulse.
        rotation.addScaledVector(vector = angularAcceleration, scale = duration)

        // Impose drag.
        velocity.scalarMultiplication(linearDamping.pow(duration))
        rotation.scalarMultiplication(angularDamping.pow(duration))

        // Adjust positions
        // Update linear position.
        position.addScaledVector(vector = velocity, scale = duration)

        // Update angular position.
        orientation.addScaledVector(vector = rotation, scale = duration)

        // Impose drag.
        velocity.scalarMultiplication(linearDamping.pow(duration))
        rotation.scalarMultiplication(angularDamping.pow(duration))

        // Normalize the orientation, and update the matrices with the new
        // position and orientation.
        calculateDerivedData()

        // Clear accumulators.
        clearAccumulators()
    }

    /**
     * Adds the given force the given point on the rigid body.
     * The direction of the force is given in world coordinates,
     * but the application point is given in body space. This is
     * useful for spring forces, or other forces fixed to the body.
     *
     * @param force The force to applu.
     *
     * @param point The location at witch to apply the force, in
     * body coordinates.
     */
    fun addForceAtBodyPoint(force: Vector, point: Vector) {
        val pt = getPointInWorldSpace(point)
        addForceAtPoint(force, pt)
    }

    /**
     * Converts the given point from world space into the body's
     * local space.
     *
     * @param point The point to convert, given in local space.
     *
     * @return The converted point, in world space.
     */
    fun  getPointInWorldSpace(point: Vector): Vector {
        return transformMatrix.transform(vector = point)
    }

    /**
     * Add the given force to the given pont on the rigid body.
     * Both the force and the application point are given in
     * world space. Because the force is not applied at the center
     * of mass, it may be split into both a force and torque.
     *
     * @param force The force to apply.
     *
     * @param point The location at which to apply the force, in
     * world coordinates.
     */
    fun addForceAtPoint(force: Vector, point: Vector) {
        val pt = point
        pt.subtract(vector = position)

        forceAccumulated.addCopy(vector = force)
        torqueAccumulated.addCopy(pt.vectorProduct(vector = force))
    }

    fun clearAccumulators() {
        forceAccumulated.clear()
        torqueAccumulated.clear()
    }

    fun getMass(): Double {
        return if (inverseMass == 0.0) {
            Double.MAX_VALUE
        } else {
            1 / inverseMass
        }
    }

    fun hasFiniteMass(): Boolean {
        return inverseMass >= 0.0
    }
}
package com.pg18omar.gameengineandroid.androidengine.components

import android.util.Log
import com.pg18omar.gameengineandroid.androidengine.Component
import com.pg18omar.gameengineandroid.androidengine.math.V2
import kotlin.math.absoluteValue

class Body: Component() {
    companion object{
        var bodies = ArrayList<Body>()
    }
    val GRAVITY = 9.8f
    var usingGravity: Boolean = true
    var restitution = 1f
    var isColliding = false
    var bounce = true
    //total time to calculate physics based on time elapsed between initial and final state
    var totalTime = 0f
    val fixedDeltaTime = 0.001f

    var velocity: V2 = V2(0f,0f)

    fun isGravityActive(value: Boolean){
        usingGravity = value
    }


    override fun update(deltaTime: Float) {
        this.totalTime += fixedDeltaTime
        boundsOverlap()
        bodyOverlap()
        //Reset the time to calculate physics
        if (isColliding){
            this.totalTime = 0f
            if (bounce){
                velocity *= -0.5f * restitution
                bounce = false
            }
        }
        else{
            bounce = true
        }

        if (usingGravity){
            velocity.y += (GRAVITY * totalTime)
        }

        parentGameObject.getComponent<Transform>()!!.position += velocity
    }

    private fun boundsOverlap() {

        //Handle the collision reaction with upper and lower border
        if (parentGameObject.getComponent<Transform>()!!.position.y >= parentGameObject.gameView.boundY
            || parentGameObject.getComponent<Transform>()!!.position.y <= 0){
            isColliding = true
        }
        //Handle the collision with right and left borders
        else if (parentGameObject.getComponent<Transform>()!!.position.x <= 0
            || parentGameObject.getComponent<Transform>()!!.position.x >= parentGameObject.gameView.boundX){
            isColliding = true
        }
        else
        isColliding = false
    }

    private fun bodyOverlap(){
        for (body in bodies){
            if (body == this) return
            val distance = 672
            val difference =  (parentGameObject.getComponent<Transform>()!!.position.magnitude() - body.parentGameObject.getComponent<Transform>()!!.position.magnitude()).absoluteValue
            if (difference <= distance){
                //The spheres detect collision but are not reacting how I want them to. Maybe make the body change direction directly here would be a solution
                body.isColliding = true
                isColliding = true
                return
            }
        }
    }
}
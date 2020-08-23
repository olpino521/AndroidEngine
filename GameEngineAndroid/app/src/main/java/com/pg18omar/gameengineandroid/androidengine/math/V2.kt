package com.pg18omar.gameengineandroid.androidengine.math

import kotlin.math.sqrt

class V2( var x :Float, var y :Float){

    fun zeroVector(){
        x = 0f
        y = 0f
    }
    operator fun plusAssign(other: V2){
        x += other.x
        y += other.y
    }

    operator  fun times(value: Float): V2 {
        x *= value
        y *= value
        return this
    }
    fun magnitude(): Float {
        return sqrt((x*x)+(y*y))
    }
    override fun toString(): String {
        return "X: $x, Y: $y"
    }
}
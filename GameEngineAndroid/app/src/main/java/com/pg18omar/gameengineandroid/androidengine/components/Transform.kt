package com.pg18omar.gameengineandroid.androidengine.components

import com.pg18omar.gameengineandroid.androidengine.Component
import com.pg18omar.gameengineandroid.androidengine.math.V2

class Transform : Component() {
    var position: V2 = V2(0f,0f)
    var angle: Float = 0f

    fun Translate(translation: V2){
        position += translation
    }
}
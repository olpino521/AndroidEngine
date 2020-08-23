package com.pg18omar.gameengineandroid.androidengine

open class Component{
    lateinit var parentGameObject: GameObject
    open fun update(deltaTime:Float){
        //Each component can create an update function for itself
    }
}

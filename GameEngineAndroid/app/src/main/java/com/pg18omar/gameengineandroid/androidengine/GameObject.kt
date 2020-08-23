package com.pg18omar.gameengineandroid.androidengine

import android.graphics.Canvas
import android.util.Log
import com.pg18omar.gameengineandroid.GameView
import com.pg18omar.gameengineandroid.androidengine.components.Body
import com.pg18omar.gameengineandroid.androidengine.components.Transform

@Suppress("LeakingThis")
abstract class GameObject(var gameView: GameView) {
    val TAG = "GAME_OBJECT"
    var touched : Boolean = false
    //Will contain Components
    //Transform is a must to render inside the app
    var components  = ArrayList<Component>()

    init {
        addComponent(Transform())
        gameView.gameObjects.add(this)
        //Add this gameobject to the game view so it can update and draw it
    }

    inline fun <reified T> getComponent(): T? {
        for(comp in components){
            if (comp is T) return comp
        }
        return null
    }

    fun <T> addComponent(comp: T){
        if (comp is Component)
        {
            components.add(comp as Component)
            comp.parentGameObject = this
            if (comp is Body){
                Body.bodies.add(comp as Body)
            }
        }
        else
            Log.e(TAG,"The component you are trying to add is not of type component")
    }

    abstract fun initializeGameObject()

    open fun render(canvas: Canvas){}

    abstract fun update(deltaTime: Float)

    open fun handleActionDown (eventX: Int, eventY: Int){
        touched = false
    }
}
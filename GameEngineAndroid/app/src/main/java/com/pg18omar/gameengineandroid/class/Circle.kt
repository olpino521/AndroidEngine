package com.pg18omar.gameengineandroid.`class`

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import com.pg18omar.gameengineandroid.GameView
import com.pg18omar.gameengineandroid.R
import com.pg18omar.gameengineandroid.androidengine.GameObject
import com.pg18omar.gameengineandroid.androidengine.components.Body
import com.pg18omar.gameengineandroid.androidengine.components.Transform
import com.pg18omar.gameengineandroid.androidengine.math.V2

class Circle(gameView: GameView) : GameObject(gameView) {

    lateinit var bitmap: Bitmap
    var circleTransform = getComponent<Transform>()
    lateinit var circleBody: Body

    override fun initializeGameObject(){
        addComponent(Body())
        circleBody = getComponent<Body>()!!
        circleTransform!!.position.x = 500f
        circleTransform!!.position.y = 500f
    }

    override fun update(deltaTime: Float) {
        //circleTransform!!.Translate(V2(0f,1f))
        circleBody.update(deltaTime)
    }

    override fun render(canvas: Canvas)
    {
        canvas.drawBitmap(bitmap, circleTransform!!.position.x.minus((bitmap.width /2)), circleTransform!!.position.y.minus((bitmap.height /2)), null)
        //canvas.drawBitmap(bitmap, circleTransform!!.position.x.minus((bitmap.width /2)) -200f, circleTransform!!.position.y.minus((bitmap.height /2)) -200f, Paint())
    }

    override fun handleActionDown (eventX: Int, eventY: Int){
        super.handleActionDown(eventX, eventY)

        if (eventX >= (circleTransform!!.position.x.minus(bitmap.width /2)) && eventX <= (circleTransform!!.position.x.plus(bitmap.width /2))){
            circleBody.velocity.zeroVector()
            touched = eventY >= (circleTransform!!.position.y.minus(bitmap.height /2)) && eventY <= (circleTransform!!.position.y.plus(bitmap.height /2))
        }
    }
}
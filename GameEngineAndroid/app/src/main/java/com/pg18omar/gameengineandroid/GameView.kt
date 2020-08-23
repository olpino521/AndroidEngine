package com.pg18omar.gameengineandroid

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.pg18omar.gameengineandroid.`class`.MainThread
import com.pg18omar.gameengineandroid.androidengine.GameObject
import com.pg18omar.gameengineandroid.androidengine.components.Transform


/**
 * TODO: document your custom view class.
 */
class GameView : SurfaceView, SurfaceHolder.Callback {

    private val TAG : String = "GAME_VIEW"
    val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.circle_red)
    val bitmap2: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.circle_red)
    var boundX = 0
    var boundY = 0
    var gameObjects = ArrayList<GameObject>()

    private lateinit var thread: MainThread
    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        this.setWillNotDraw(true)

        //Add callback to the surface view to intercept callback events
        holder.addCallback(this)

        thread = MainThread(holder,this)

        //Makes the view focusable so it can handle events
        focusable = View.FOCUSABLE
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {

        thread.setEngineRunning(true)
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        var retry = true
        while (retry)
        {
            try {
                thread.join();
                retry = false
            }
            catch (e: Exception)
            {
                print(e)
            }
        }
    }

    fun update(deltaTime : Float)
    {
        //Update the game view objects
        for (go in gameObjects){
            go.update(deltaTime)
        }
    }

    fun render(canvas: Canvas?)
    {
        draw(canvas)
        //Render the game view objects
        for (go in gameObjects){
            go.render(canvas!!)
        }
        invalidate()
    }

    fun initializeGo(){
        for (go in gameObjects){
            go.initializeGameObject()
        }
    }

    fun setBoundaries(width: Int, height: Int){
        boundX = width
        boundY = height
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_DOWN){
            for (go in gameObjects){
                go.handleActionDown(event.x.toInt(),event.y.toInt())
            }

        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            for (go in gameObjects){
                if (go.touched)
                {
                    go.getComponent<Transform>()!!.position.x = event.x
                    go.getComponent<Transform>()!!.position.y = event.y
                }
            }


        }

        if (event.action == MotionEvent.ACTION_UP) {
            for (go in gameObjects){
                go.touched = false
            }
        }
        return true
//        return super.onTouchEvent(event)
    }
}

package com.pg18omar.gameengineandroid

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.pg18omar.gameengineandroid.`class`.Circle
import com.pg18omar.gameengineandroid.androidengine.components.Body


class MainActivity : AppCompatActivity() {

    private val TAG : String = "MAIN_ACTIVITY"
    lateinit var circle: Circle
    lateinit var circle2: Circle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val gv = GameView(this)

        circle = Circle(gv)
        circle.bitmap = gv.bitmap

        circle2 = Circle(gv)
        circle2.bitmap = gv.bitmap2

        Log.d(TAG, "View Added")
        val wm = windowManager
        val d = wm.defaultDisplay
        val size = Point()
        d.getSize(size)
        gv.setBoundaries(size.x,size.y)
        gv.initializeGo()
        setContentView(gv)
        circle2.circleTransform!!.position.x = circle.circleTransform!!.position.x
        circle2.circleTransform!!.position.y = 1300f
    }

    override fun onDestroy() {
        Log.d(TAG,"Destroying...")
        super.onDestroy()
    }

    override fun onStop() {
        Log.d(TAG,"Stopping...")
        super.onStop()
    }
}
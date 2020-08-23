package com.pg18omar.gameengineandroid.`class`

import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import com.pg18omar.gameengineandroid.GameView

class MainThread(sh: SurfaceHolder, gv: GameView) : Thread() {

    private val TAG : String = "MAIN_THREAD"
    private val FPS = 96
    private val MAX_FRAME_SKIP = 5
    private val FRAME_PERIOD = 1000 / FPS

    // the average FPS since the game sta
    private var running: Boolean = false
    private var surfaceHolder: SurfaceHolder = sh
    private var gameView: GameView = gv
    var canvas: Canvas? = null

    fun setEngineRunning(value: Boolean)
    {
        running = value
    }

    override fun run() {
        var startTime: Long
        var deltaTime = 0L
        var sleepTime: Long
        var frameSkipped: Int
        Log.d(TAG,"Started Game Loop")
        //Moved the game view initialization pf the gameobjects to the Activity for more control over it
        while (running) {

            try {
                canvas = surfaceHolder.lockCanvas()

                synchronized(surfaceHolder)
                {
                    startTime = System.currentTimeMillis()
                    frameSkipped = 0
                    // update game state
                    gameView.update(deltaTime.toFloat() / 1000)

                    // draws the canvas on the panel
                    gameView.render(canvas)
                    //How long it took the frame to finish its cycle. I think
                    deltaTime = System.currentTimeMillis() - startTime

                    //Time left in the cycle which the thread will sleep for constant game speed
                    sleepTime = (FRAME_PERIOD - deltaTime)

                    //If the sleep time is greater than zero. We are ahead so we just have to wait for the time to pass
                    if (sleepTime > 0) {
                        try {
                            sleep(sleepTime)
                        } catch (e: Exception) {
                        }
                    }

                    //If the sleep time is negative. Update until the sleep time has passed or until a max of skipped frames is reached
                    while (sleepTime < 0 && frameSkipped < MAX_FRAME_SKIP) {
                        gameView.update(deltaTime.toFloat())

                        sleepTime += FRAME_PERIOD
                        frameSkipped++
                    }
                }

            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }
}
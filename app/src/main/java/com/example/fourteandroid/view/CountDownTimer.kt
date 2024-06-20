package com.example.fourteandroid.view

import android.os.Handler
import android.os.Looper

class CountDownTimer(
    private val initialMillisInFuture: Long,
    private val countDownInterval: Long,
    private val onTick: (Long) -> Unit,
    private val onFinish: () -> Unit
) {
    private var remainingTime = initialMillisInFuture
    private var isPaused = false
    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }
    private var runnable: Runnable? = null

//    init {
//        handler = Handler(Looper.getMainLooper())
//    }

    fun start() {
        if (runnable == null) {
            runnable = object : Runnable {
                override fun run() {
                    if (isPaused) {
                        return
                    }
                    if (remainingTime <= 0) {
                        onFinish()
                    } else {
                        onTick(remainingTime)
                        remainingTime -= countDownInterval
                        handler.postDelayed(this, countDownInterval)
                    }
                }
            }
        }
        isPaused = false
        handler.post(runnable!!)
    }

    fun pause() {
        isPaused = true
    }

    fun resume() {
        if (isPaused) {
            isPaused = false
            handler?.post(runnable!!)
        }
    }

    fun cancel() {
        if (handler!=null){
            handler?.removeCallbacks(runnable!!)
            isPaused = false
        }

    }

    fun reset() {
        cancel()
        remainingTime = initialMillisInFuture
        start()
    }
}


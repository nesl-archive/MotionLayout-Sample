package com.example.labmotion.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.labmotion.R

// https://medium.com/vrt-digital-studio/picture-in-picture-video-overlay-with-motionlayout-a9404663b9e7
class VideoOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val motionLayout: MotionLayout =
        LayoutInflater.from(context).inflate(R.layout.blank_fragment, this, false) as MotionLayout

    private val touchableArea: View =  motionLayout
    private val clickableArea: View = motionLayout

    private var startX: Float? = null
    private var startY: Float? = null

    init {
        addView(motionLayout)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val isInProgress = (motionLayout.progress > 0.0f && motionLayout.progress < 1.0f)
        val isInTarget = touchEventInsideTargetView(touchableArea, ev)

        return if (isInProgress || isInTarget) {
            super.onInterceptTouchEvent(ev)
        } else {
            true
        }
    }

    private fun touchEventInsideTargetView(v: View, ev: MotionEvent): Boolean {
        if (ev.x > v.left && ev.x < v.right) {
            if (ev.y > v.top && ev.y < v.bottom) {
                return true
            }
        }
        return false
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (touchEventInsideTargetView(clickableArea, ev)) {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = ev.x
                    startY = ev.y
                }

                MotionEvent.ACTION_UP   -> {
                    val endX = ev.x
                    val endY = ev.y
                    if (isAClick(startX!!, endX, startY!!, endY)) {
                        if (doClickTransition()) {
                            return true
                        }
                    }
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun doClickTransition(): Boolean {
        var isClickHandled = false
        if (motionLayout.progress < 0.05F) {
            motionLayout.transitionToEnd()
            isClickHandled = true
        } else if (motionLayout.progress > 0.95F) {
            motionLayout.transitionToStart()
            isClickHandled = true
        }
        return isClickHandled
    }

    private fun isAClick(startX: Float, endX: Float, startY: Float, endY: Float): Boolean {
        val differenceX = Math.abs(startX - endX)
        val differenceY = Math.abs(startY - endY)
        return !/* =5 */(differenceX > 200 || differenceY > 200)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }

}

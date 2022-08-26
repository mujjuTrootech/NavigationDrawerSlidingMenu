package com.trootech.sliderlibrary.slider.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import com.trootech.sliderlibrary.slider.SlidingRootNavLayout

class HiddenMenuClickConsumer(context: Context?) : View(context) {
    private var menuHost: SlidingRootNavLayout? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return menuHost!!.isMenuClosed
    }

    fun setMenuHost(layout: SlidingRootNavLayout?) {
        menuHost = layout
    }
}
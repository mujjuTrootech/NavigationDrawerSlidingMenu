package com.trootech.navilibrary.slider.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import com.trootech.navilibrary.slider.DrawerRootNavLayout

/**
 * Created by TrooTech solution on 26.08.2022.
 */
class HiddenMenuClickConsumer(context: Context?) : View(context) {
    private var menuHost: DrawerRootNavLayout? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return menuHost!!.isMenuClosed
    }

    fun setMenuHost(layout: DrawerRootNavLayout?) {
        menuHost = layout
    }
}
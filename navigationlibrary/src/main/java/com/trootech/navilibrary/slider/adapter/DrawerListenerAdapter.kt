package com.trootech.navilibrary.slider.adapter

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.trootech.navilibrary.slider.callback.DragListener
import com.trootech.navilibrary.slider.callback.DragStateListener


/**
 * Created by TrooTech solution on 26.08.2022.
 */
class DrawerListenerAdapter(private val adaptee: DrawerListener, private val drawer: View?) :
    DragListener, DragStateListener {
    override fun onDrag(progress: Float) {
        adaptee.onDrawerSlide(drawer!!, progress)
    }

    override fun onDragStart() {
        adaptee.onDrawerStateChanged(DrawerLayout.STATE_DRAGGING)
    }

    override fun onDragEnd(isMenuOpened: Boolean) {
        if (isMenuOpened) {
            adaptee.onDrawerOpened(drawer!!)
        } else {
            adaptee.onDrawerClosed(drawer!!)
        }
        adaptee.onDrawerStateChanged(DrawerLayout.STATE_IDLE)
    }
}
package com.trootech.sliderlibrary.slider.callback

interface DragStateListener {
    fun onDragStart()
    fun onDragEnd(isMenuOpened: Boolean)
}
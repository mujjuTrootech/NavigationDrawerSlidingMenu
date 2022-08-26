package com.trootech.sliderlibrary.slider.transform

import android.view.*
import com.trootech.sliderlibrary.slider.utils.SideNavUtils


class ScaleTransformation(private val endScale: Float) : RootTransformation {
    override fun transform(dragProgress: Float, rootView: View?) {
        val scale = SideNavUtils.evaluate(dragProgress, START_SCALE, endScale)
        rootView!!.scaleX = scale
        rootView.scaleY = scale
    }

    companion object {
        private const val START_SCALE = 1f
    }
}
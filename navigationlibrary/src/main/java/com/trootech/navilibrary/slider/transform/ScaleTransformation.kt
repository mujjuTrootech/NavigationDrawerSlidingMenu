package com.trootech.navilibrary.slider.transform

import android.view.*
import com.trootech.navilibrary.slider.utils.DrawerNavUtils


/**
 * Created by TrooTech solution on 26.08.2022.
 */
class ScaleTransformation(private val endScale: Float) : RootTransformation {
    override fun transform(dragProgress: Float, rootView: View?) {
        val scale = DrawerNavUtils.evaluate(dragProgress, START_SCALE, endScale)
        rootView!!.scaleX = scale
        rootView.scaleY = scale
    }

    companion object {
        private const val START_SCALE = 1f
    }
}
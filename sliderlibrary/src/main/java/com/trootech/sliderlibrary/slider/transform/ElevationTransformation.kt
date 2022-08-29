package com.trootech.sliderlibrary.slider.transform

import android.os.Build
import android.view.View
import com.trootech.sliderlibrary.slider.utils.SideNavUtils


/**
 * Created by TrooTech solution on 26.08.2022.
 */
class ElevationTransformation(private val endElevation: Float) : RootTransformation {
    override fun transform(dragProgress: Float, rootView: View?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val elevation = SideNavUtils.evaluate(dragProgress, START_ELEVATION, endElevation)
            rootView!!.elevation = elevation
        }
    }

    companion object {
        private const val START_ELEVATION = 0f
    }
}
package com.trootech.sliderlibrary.slider.transform

import android.view.View


/**
 * Created by TrooTech solution on 26.08.2022.
 */
interface RootTransformation {
    fun transform(dragProgress: Float, rootView: View?)
}
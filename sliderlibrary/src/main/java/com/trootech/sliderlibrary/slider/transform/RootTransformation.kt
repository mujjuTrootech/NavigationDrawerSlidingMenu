package com.trootech.sliderlibrary.slider.transform

import android.view.View

interface RootTransformation {
    fun transform(dragProgress: Float, rootView: View?)
}
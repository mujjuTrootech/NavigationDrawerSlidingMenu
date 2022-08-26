package com.trootech.sliderlibrary.slider.transform

import android.view.View

class CompositeTransformation(private val transformations: List<RootTransformation>) :
    RootTransformation {
    override fun transform(dragProgress: Float, rootView: View?) {
        for (t in transformations) {
            t.transform(dragProgress, rootView)
        }
    }
}
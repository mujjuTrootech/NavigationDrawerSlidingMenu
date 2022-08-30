package com.trootech.navilibrary.slider.transform

import android.view.View


/**
 * Created by TrooTech solution on 26.08.2022.
 */
class CompositeTransformation(private val transformations: List<RootTransformation>) :
    RootTransformation {
    override fun transform(dragProgress: Float, rootView: View?) {
        for (t in transformations) {
            t.transform(dragProgress, rootView)
        }
    }
}
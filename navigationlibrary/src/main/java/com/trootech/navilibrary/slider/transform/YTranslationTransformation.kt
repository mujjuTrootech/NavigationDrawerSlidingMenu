package com.trootech.navilibrary.slider.transform

import android.view.View
import com.trootech.navilibrary.slider.utils.DrawerNavUtils


/**
 * Created by TrooTech solution on 26.08.2022.
 */
class YTranslationTransformation(private val endTranslation: Float) : RootTransformation {
    override fun transform(dragProgress: Float, rootView: View?) {
        val translation = DrawerNavUtils.evaluate(dragProgress, START_TRANSLATION, endTranslation)
        rootView!!.translationY = translation
    }

    companion object {
        private const val START_TRANSLATION = 0f
    }
}
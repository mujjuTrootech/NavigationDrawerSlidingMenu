package com.trootech.sliderlibrary.slider.utils

object SideNavUtils {
    fun evaluate(fraction: Float, startValue: Float, endValue: Float): Float {
        return startValue + fraction * (endValue - startValue)
    }
}
package com.trootech.navilibrary.slider.utils

/**
 * Created by TrooTech solution on 26.08.2022.
 */
object DrawerNavUtils {
    fun evaluate(fraction: Float, startValue: Float, endValue: Float): Float {
        return startValue + fraction * (endValue - startValue)
    }
}
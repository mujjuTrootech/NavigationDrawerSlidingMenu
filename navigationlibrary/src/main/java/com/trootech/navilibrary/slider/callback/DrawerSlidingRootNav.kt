package com.trootech.navilibrary.slider.callback

import com.trootech.navilibrary.slider.DrawerRootNavLayout


/**
 * Created by TrooTech solution on 26.08.2022.
 */
interface DrawerSlidingRootNav {
    val isMenuClosed: Boolean
    val isMenuOpened: Boolean
    val layout: DrawerRootNavLayout

    var isMenuLocked: Boolean
    fun closeMenu()
    fun closeMenu(animated: Boolean)
    fun openMenu()
    fun openMenu(animated: Boolean)
}
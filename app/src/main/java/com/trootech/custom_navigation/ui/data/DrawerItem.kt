package com.trootech.custom_navigation.ui.data

import android.view.ViewGroup
import com.trootech.custom_navigation.ui.adtr.DrawerAdapter


public abstract class DrawerItem<T : DrawerAdapter.ViewHolder?> {

    var isSelectChecked:Boolean = false

    abstract fun createViewHolder(parent: ViewGroup?): T
    abstract fun bindViewHolder(holder: T)

    fun setChecked(isChecked: Boolean?): DrawerItem<T> {
        this.isSelectChecked = isChecked!!
        return this
    }

    fun  isChecked(): Boolean{
        return isSelectChecked
    }


    fun  isSelectable():Boolean{
        return true
    }


}
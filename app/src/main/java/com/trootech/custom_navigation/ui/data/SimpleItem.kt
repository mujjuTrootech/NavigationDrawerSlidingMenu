package com.trootech.custom_navigation.ui.data

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.trootech.custom_navigation.R

import android.widget.TextView
import com.trootech.custom_navigation.ui.adtr.DrawerAdapter

/*
* Drawer Item view create and selected item managed. Like a icon and title.
* */
class SimpleItem(private val icon: Drawable, private val title: String)
    : DrawerItem<SimpleItem.ViewHolder>() {

    private var selectedItemIconTint = 0
    private var selectedItemTextTint = 0
    private var normalItemIconTint = 0
    private var normalItemTextTint = 0

    override fun createViewHolder(parent: ViewGroup?): ViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.item_option, parent, false)
        return ViewHolder(v)
    }

    override fun bindViewHolder(holder: ViewHolder) {
        holder.title.text = title
        holder.icon.setImageDrawable(icon)
        holder.title.setTextColor(if (isSelectChecked) selectedItemTextTint else normalItemTextTint)
        holder.icon.setColorFilter(if (isSelectChecked) selectedItemIconTint else normalItemIconTint)
    }


    fun withSelectedIconTint(selectedItemIconTint: Int): SimpleItem {
        this.selectedItemIconTint = selectedItemIconTint
        return this
    }

    fun withSelectedTextTint(selectedItemTextTint: Int): SimpleItem {
        this.selectedItemTextTint = selectedItemTextTint
        return this
    }

    fun withIconTint(normalItemIconTint: Int): SimpleItem {
        this.normalItemIconTint = normalItemIconTint
        return this
    }

    fun withTextTint(normalItemTextTint: Int): SimpleItem {
        this.normalItemTextTint = normalItemTextTint
        return this
    }

    public class ViewHolder(itemView: View) : DrawerAdapter.ViewHolder(itemView) {
        public val icon: ImageView
        public val title: TextView

        init {
            icon = itemView.findViewById<View>(R.id.icon) as ImageView
            title = itemView.findViewById<View>(R.id.title) as TextView
        }
    }

}
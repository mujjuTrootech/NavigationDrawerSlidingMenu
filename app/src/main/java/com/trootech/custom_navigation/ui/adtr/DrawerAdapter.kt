package com.trootech.custom_navigation.ui.adtr

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.trootech.custom_navigation.ui.data.DrawerItem


class DrawerAdapter() :
    RecyclerView.Adapter<DrawerAdapter.ViewHolder>() {

    lateinit var items: List<DrawerItem<DrawerAdapter.ViewHolder>>
    lateinit var viewTypes: MutableMap<Class<out DrawerItem<*>>, Int>
    lateinit var holderFactories: SparseArray<DrawerItem<*>>
    private var listener: OnItemSelectedListener? = null

    /*
    *initialization arraylist with holder
    * */
    constructor(items: List<DrawerItem<*>>) : this() {
        this.items = items as List<DrawerItem<DrawerAdapter.ViewHolder>>
        this.viewTypes = HashMap()
        this.holderFactories = SparseArray<DrawerItem<*>>()
        processViewTypes()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = holderFactories[viewType].createViewHolder(parent)
        holder!!.adapter = this
        return holder
    }

    @SuppressWarnings("unchecked")
    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        items[position].bindViewHolder(holder)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypes[items[position].javaClass]!!
    }

    private fun processViewTypes() {
        var type = 0
        for (item in items) {
            if (!viewTypes.containsKey(item.javaClass)) {
                viewTypes[item.javaClass] = type
                holderFactories.put(type, item)
                type++
            }
        }
    }

    /*
    * Slide menu selected item only notify
    * */
    fun setSelected(position: Int) {
        val newChecked = items[position]
        if (!newChecked.isSelectable()) {
            return
        }
        for (i in items.indices) {
            val item = items[i]
            if (item.isSelectChecked) {
                item.isSelectChecked = false
                notifyItemChanged(i)
                break
            }
        }
        newChecked.isSelectChecked = true
        notifyItemChanged(position)
        if (listener != null) {
            listener!!.onItemSelected(position)
        }
    }

    fun setListener(listener: OnItemSelectedListener?) {
        this.listener = listener
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var adapter: DrawerAdapter? = null

        override fun onClick(v: View) {
            adapter!!.setSelected(adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    interface OnItemSelectedListener {
        fun onItemSelected(position: Int)
    }

}
package com.trootech.custom_navigation.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trootech.custom_navigation.R

import com.trootech.custom_navigation.ui.adtr.DrawerAdapter
import com.trootech.custom_navigation.ui.data.SimpleItem
import com.trootech.custom_navigation.ui.data.SpaceItem
import com.trootech.sliderlibrary.slider.SlidingRootNavBuilder
import com.trootech.sliderlibrary.slider.callback.SlidingRootNav
import java.util.*


class DashboardActivity : AppCompatActivity(),
    DrawerAdapter.OnItemSelectedListener {

    //Drawer related title values set
    lateinit var  screenTitles: Array<String>
    //Drawer related icon values set if needed show in navigation drawer
    lateinit var  screenIcons: Array<Drawable?>
    private var slidingRootNav: SlidingRootNav? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //Initialization root view and access all filed.
        slidingRootNav =  SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)//If first time load screen at a time menu open required so set tru values
                .withContentClickableWhenMenuOpened(true)//if needed after open navigation drawer back menu clicked drawer closed so values add true.
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons()
        screenTitles = loadScreenTitles()

        //Navigation drawer set list or menu item.
        val arrayList = listOf(
            createItemFor(POS_DASHBOARD).setChecked(true),//checked true means screen load time first this screen open & drawer side select this item.
            createItemFor(POS_ACCOUNT),
            createItemFor(POS_MESSAGES),
            createItemFor(POS_CART),
            SpaceItem(48),
            createItemFor(POS_LOGOUT)
        )
        val adapter = DrawerAdapter(arrayList)
        adapter.setListener(this)

        val list = findViewById<RecyclerView>(R.id.list)
        list.isNestedScrollingEnabled = false
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        adapter.setSelected(POS_DASHBOARD)//Default set dashboard if required any other so changed screen name
    }

    /*
    * Item selected time replace screen and closed drawer menu.
    * */
    override fun onItemSelected(position: Int) {
        if (position == POS_LOGOUT) {
            finish()
        }
        slidingRootNav!!.closeMenu()//Closed drawer menu clicked managed function.
        val selectedScreen: Fragment = DashboardFragment.
        createFor(screenTitles[position])
        showFragment(selectedScreen)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment)
            .commit()
    }

    //Drawer item title and icon select and unselect color managed here. as per requirement changed this color code.
    private fun createItemFor(position: Int): SimpleItem {
        return SimpleItem(screenIcons[position]!!, screenTitles[position])
            .withIconTint(color(R.color.gray))
            .withTextTint(color(R.color.gray))
            .withSelectedIconTint(color(R.color.purple_700))
            .withSelectedTextTint(color(R.color.purple_700))
    }

    /*
    * Drawer arraylist title managed
    * */
    private fun loadScreenTitles(): Array<String> {
        return resources.getStringArray(R.array.ld_activityScreenTitles)
    }


    /*
    * Drawer arraylist drawer managed
    * */
    private fun loadScreenIcons(): Array<Drawable?> {
        val ta = resources.obtainTypedArray(R.array.ld_activityScreenIcons)
        val icons = arrayOfNulls<Drawable>(ta.length())
        for (i in 0 until ta.length()) {
            val id = ta.getResourceId(i, 0)
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id)
            }
        }
        ta.recycle()
        return icons
    }

    @ColorInt
    private fun color(@ColorRes res: Int): Int {
        return ContextCompat.getColor(this, res)
    }

    companion object {
        private const val POS_DASHBOARD = 0
        private const val POS_ACCOUNT = 1
        private const val POS_MESSAGES = 2
        private const val POS_CART = 3
        private const val POS_LOGOUT = 5
    }




}
package com.trootech.custom_navigation.ui


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
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
import com.trootech.navilibrary.slider.DrawerGravity
import com.trootech.navilibrary.slider.DrawerRootNavBuilder
import com.trootech.navilibrary.slider.callback.DrawerSlidingRootNav


class DashboardActivity : AppCompatActivity(),
    DrawerAdapter.OnItemSelectedListener {

    //Drawer related title/icon values set
    private lateinit var screenTitles: Array<String>
    private lateinit var screenIcons: Array<Drawable?>

    //Interface through root view managed. Like Drawer open/closed/layout etc managed.
    private var slidingRootNav: DrawerSlidingRootNav? = null

    private lateinit var ivMenuLeft: ImageView
    private lateinit var ivMenuRight: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var toolbar: Toolbar

    private var isLeftRight: Boolean = false //Default:false -Left, true-Right
    private var gravityView: DrawerGravity = DrawerGravity.LEFT //Default:LEFT
    private var gravityLayout: Int = R.layout.drawer_menu_left

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        toolbar = findViewById(R.id.toolbar)
        ivMenuLeft = findViewById(R.id.ivMenuLeft)
        ivMenuRight = findViewById(R.id.ivMenuRight)
        tvTitle = findViewById(R.id.tvTitle)

        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false) //Menu hide
            supportActionBar!!.setDisplayShowTitleEnabled(false) //Header Title
            supportActionBar?.setHomeButtonEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
            ivMenuRight.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_menu))
            ivMenuLeft.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_menu))
        }

        setListener()

        //Set per required menu open right/left so managed here
        isLeftRight = true
        if (isLeftRight) {
            gravityView = DrawerGravity.RIGHT
            gravityLayout = R.layout.drawer_menu_right
            ivMenuRight.visibility = View.VISIBLE
            ivMenuLeft.visibility = View.GONE
        } else {
            gravityView = DrawerGravity.LEFT
            gravityLayout = R.layout.drawer_menu_left
            ivMenuRight.visibility = View.GONE
            ivMenuLeft.visibility = View.VISIBLE
        }

        //Initialization root view and access all filed.
        slidingRootNav = DrawerRootNavBuilder(this)
            //.withToolbarMenuToggle(toolbar) //Must be need to initialization toolbar. Without toolbar not slid menu visible.
            .withGravity(gravityView)
            .withSavedState(savedInstanceState)
            .withMenuLayout(gravityLayout)
            .inject()

        screenIcons = loadScreenIcons()
        screenTitles = loadScreenTitles()
        tvTitle.text = screenTitles[0] //Set title name as per visible view

        //Navigation drawer set list or menu item.
        val arrayList = listOf(
            createItemFor(POS_DASHBOARD),
            createItemFor(POS_ACCOUNT),
            createItemFor(POS_MESSAGES),
            createItemFor(POS_CART),
            SpaceItem(48),//Space changed as per item list through required
            createItemFor(POS_LOGOUT)
        )
        val adapter = DrawerAdapter(arrayList)
        adapter.setListener(this)

        val list = findViewById<RecyclerView>(R.id.list)
        list.isNestedScrollingEnabled = false
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter

        //If need to first time initialization Time show any position default select show pass this item position.
        adapter.setSelected(POS_DASHBOARD)//Default set dashboard if required any other so changed screen name
    }

    private fun setListener() {
        ivMenuLeft.setOnClickListener {
            openClosedDrawer()
        }

        ivMenuRight.setOnClickListener {
            openClosedDrawer()
        }
    }

    private fun openClosedDrawer() {
        if (slidingRootNav!!.isMenuClosed) {
            slidingRootNav!!.openMenu()
            ivMenuLeft.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_back))
            ivMenuRight.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_back))
        } else {
            slidingRootNav!!.closeMenu()
            ivMenuLeft.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_menu))
            ivMenuRight.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_menu))
        }
    }


    /*
    * Item selected time replace screen and closed drawer menu.
    * */
    override fun onItemSelected(position: Int) {
        slidingRootNav!!.closeMenu()//Closed drawer menu clicked managed function.
        ivMenuLeft.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_menu))
        ivMenuRight.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_menu))

        //As per required position wise fragment view changed.
        if (position == POS_LOGOUT) {
            finish()
        }

        val selectedScreen: Fragment = DashboardFragment.createFor(screenTitles[position])
        showFragment(selectedScreen)

        tvTitle.text = screenTitles[position]
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
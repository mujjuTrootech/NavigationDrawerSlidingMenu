package com.trootech.navilibrary.slider

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import com.trootech.navilibrary.slider.adapter.ActionBarToggleAdapter
import com.trootech.navilibrary.slider.adapter.DrawerListenerAdapter
import com.trootech.navilibrary.slider.callback.DragListener
import com.trootech.navilibrary.slider.callback.DragStateListener
import com.trootech.navilibrary.slider.callback.DrawerSlidingRootNav
import com.trootech.navilibrary.slider.transform.*
import com.trootech.navilibrary.slider.utils.HiddenMenuClickConsumer
import com.trootech.sliderlibrary.R

import java.util.*

/**
 * Created by TrooTech solution on 26.08.2022.
 */
class DrawerRootNavBuilder(private val activity: Activity) {
    private var contentView: ViewGroup? = null
    private var menuView: View? = null
    private var menuLayoutRes = 0
    private val transformations: MutableList<RootTransformation>
    private val dragListeners: MutableList<DragListener>
    private val dragStateListeners: MutableList<DragStateListener>
    private var dragDistance: Int
    private var toolbar: Toolbar? = null
    private var gravity: DrawerGravity
    private var isMenuOpened = false
    private var isMenuLocked = false
    private var isContentClickableWhenMenuOpened: Boolean
    private var savedState: Bundle? = null
    fun withMenuView(view: View?): DrawerRootNavBuilder {
        menuView = view
        return this
    }

    fun withMenuLayout(@LayoutRes layout: Int): DrawerRootNavBuilder {
        menuLayoutRes = layout
        return this
    }

    fun withToolbarMenuToggle(tb: Toolbar?): DrawerRootNavBuilder {
        toolbar = tb
        return this
    }

    fun withGravity(g: DrawerGravity): DrawerRootNavBuilder {
        gravity = g
        return this
    }

    fun withContentView(cv: ViewGroup?): DrawerRootNavBuilder {
        contentView = cv
        return this
    }

    fun withMenuLocked(locked: Boolean): DrawerRootNavBuilder {
        isMenuLocked = locked
        return this
    }

    fun withSavedState(state: Bundle?): DrawerRootNavBuilder {
        savedState = state
        return this
    }

    fun withMenuOpened(opened: Boolean): DrawerRootNavBuilder {
        isMenuOpened = opened
        return this
    }

    fun withContentClickableWhenMenuOpened(clickable: Boolean): DrawerRootNavBuilder {
        isContentClickableWhenMenuOpened = clickable
        return this
    }

    fun withDragDistance(dp: Int): DrawerRootNavBuilder {
        return withDragDistancePx(dpToPx(dp))
    }

    fun withDragDistancePx(px: Int): DrawerRootNavBuilder {
        dragDistance = px
        return this
    }

    fun withRootViewScale(@FloatRange(from = 0.01) scale: Float): DrawerRootNavBuilder {
        transformations.add(ScaleTransformation(scale))
        return this
    }

    fun withRootViewElevation(@IntRange(from = 0) elevation: Int): DrawerRootNavBuilder {
        return withRootViewElevationPx(dpToPx(elevation))
    }

    fun withRootViewElevationPx(@IntRange(from = 0) elevation: Int): DrawerRootNavBuilder {
        transformations.add(ElevationTransformation(elevation.toFloat()))
        return this
    }

    fun withRootViewYTranslation(translation: Int): DrawerRootNavBuilder {
        return withRootViewYTranslationPx(dpToPx(translation))
    }

    fun withRootViewYTranslationPx(translation: Int): DrawerRootNavBuilder {
        transformations.add(YTranslationTransformation(translation.toFloat()))
        return this
    }

    fun addRootTransformation(transformation: RootTransformation): DrawerRootNavBuilder {
        transformations.add(transformation)
        return this
    }

    fun addDragListener(dragListener: DragListener): DrawerRootNavBuilder {
        dragListeners.add(dragListener)
        return this
    }

    fun addDragStateListener(dragStateListener: DragStateListener): DrawerRootNavBuilder {
        dragStateListeners.add(dragStateListener)
        return this
    }

    fun inject(): DrawerSlidingRootNav {
        val contentView = getContentView()
        val oldRoot = contentView!!.getChildAt(0)
        contentView.removeAllViews()
        val newRoot = createAndInitNewRoot(oldRoot)
        val menu = getMenuViewFor(newRoot)
        initToolbarMenuVisibilityToggle(newRoot, menu)
        val clickConsumer = HiddenMenuClickConsumer(activity)
        clickConsumer.setMenuHost(newRoot)
        newRoot.addView(menu)
        newRoot.addView(clickConsumer)
        newRoot.addView(oldRoot)
        contentView.addView(newRoot)
        if (savedState == null) {
            if (isMenuOpened) {
                newRoot.openMenu(false)
            }
        }
        newRoot.isMenuLocked = isMenuLocked
        return newRoot
    }

    private fun createAndInitNewRoot(oldRoot: View): DrawerRootNavLayout {
        val newRoot = DrawerRootNavLayout(activity)
        newRoot.id = R.id.srn_root_layout
        newRoot.setRootTransformation(createCompositeTransformation())
        newRoot.setMaxDragDistance(dragDistance)
        newRoot.setGravity(gravity)
        newRoot.rootView = oldRoot
        newRoot.setContentClickableWhenMenuOpened(isContentClickableWhenMenuOpened)
        for (l in dragListeners) {
            newRoot.addDragListener(l)
        }
        for (l in dragStateListeners) {
            newRoot.addDragStateListener(l)
        }
        return newRoot
    }

    private fun getContentView(): ViewGroup? {
        if (contentView == null) {
            contentView = activity.findViewById<View>(android.R.id.content) as ViewGroup
        }
        check(contentView!!.childCount == 1) { activity.getString(R.string.srn_ex_bad_content_view) }
        return contentView
    }

    private fun getMenuViewFor(parent: DrawerRootNavLayout): View? {
        if (menuView == null) {
            check(menuLayoutRes != 0) { activity.getString(R.string.srn_ex_no_menu_view) }
            menuView = LayoutInflater.from(activity).inflate(menuLayoutRes, parent, false)
        }
        return menuView
    }

    private fun createCompositeTransformation(): RootTransformation {
        return if (transformations.isEmpty()) {
            CompositeTransformation(
                Arrays.asList(
                    ScaleTransformation(DEFAULT_END_SCALE),
                    ElevationTransformation(dpToPx(DEFAULT_END_ELEVATION_DP).toFloat())
                )
            )
        } else {
            CompositeTransformation(transformations)
        }
    }

    protected fun initToolbarMenuVisibilityToggle(sideNav: DrawerRootNavLayout, drawer: View?) {
        if (toolbar != null) {
            val dlAdapter = ActionBarToggleAdapter(activity)
            dlAdapter.setAdaptee(sideNav)
            val toggle = ActionBarDrawerToggle(
                activity, dlAdapter, toolbar,
                R.string.srn_drawer_open,
                R.string.srn_drawer_close
            )
            toggle.syncState()
            val listenerAdapter = DrawerListenerAdapter(toggle, drawer)
            sideNav.addDragListener(listenerAdapter)
            sideNav.addDragStateListener(listenerAdapter)
        }
    }

    private fun dpToPx(dp: Int): Int {
        return Math.round(activity.resources.displayMetrics.density * dp)
    }

    companion object {
        private const val DEFAULT_END_SCALE = 0.65f
        private const val DEFAULT_END_ELEVATION_DP = 8
        private const val DEFAULT_DRAG_DIST_DP = 180
    }

    init {
        transformations = ArrayList()
        dragListeners = ArrayList()
        dragStateListeners = ArrayList()
        gravity = DrawerGravity.LEFT
        dragDistance = dpToPx(DEFAULT_DRAG_DIST_DP)
        isContentClickableWhenMenuOpened = true
    }
}
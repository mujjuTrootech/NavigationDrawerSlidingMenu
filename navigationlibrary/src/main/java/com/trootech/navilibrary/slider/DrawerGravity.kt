package com.trootech.navilibrary.slider

import androidx.customview.widget.ViewDragHelper


/**
 * Created by TrooTech solution on 26.08.2022.
 */

enum class DrawerGravity {
    LEFT {
        override fun createHelper(): Helper {
            return LeftHelper()
        }
    },
    RIGHT {
        override fun createHelper(): Helper {
            return RightHelper()
        }
    };

    abstract fun createHelper(): Helper
    interface Helper {
        fun getLeftAfterFling(flingVelocity: Float, maxDrag: Int): Int
        fun getLeftToSettle(dragProgress: Float, maxDrag: Int): Int
        fun getRootLeft(dragProgress: Float, maxDrag: Int): Int
        fun getDragProgress(viewLeft: Int, maxDrag: Int): Float
        fun clampViewPosition(left: Int, maxDrag: Int): Int
        fun enableEdgeTrackingOn(dragHelper: ViewDragHelper)
    }

    internal class LeftHelper : Helper {
        override fun getLeftAfterFling(flingVelocity: Float, maxDrag: Int): Int {
            return if (flingVelocity > 0) maxDrag else 0
        }

        override fun getLeftToSettle(dragProgress: Float, maxDrag: Int): Int {
            return if (dragProgress > 0.5f) maxDrag else 0
        }

        override fun getRootLeft(dragProgress: Float, maxDrag: Int): Int {
            return (dragProgress * maxDrag).toInt()
        }

        override fun getDragProgress(viewLeft: Int, maxDrag: Int): Float {
            return viewLeft.toFloat() / maxDrag
        }

        override fun clampViewPosition(left: Int, maxDrag: Int): Int {
            return Math.max(0, Math.min(left, maxDrag))
        }

        override fun enableEdgeTrackingOn(dragHelper: ViewDragHelper) {
            dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
        }
    }

    internal class RightHelper : Helper {
        override fun getLeftAfterFling(flingVelocity: Float, maxDrag: Int): Int {
            return if (flingVelocity > 0) 0 else -maxDrag
        }

        override fun getLeftToSettle(dragProgress: Float, maxDrag: Int): Int {
            return if (dragProgress > 0.5f) -maxDrag else 0
        }

        override fun getRootLeft(dragProgress: Float, maxDrag: Int): Int {
            return -(dragProgress * maxDrag).toInt()
        }

        override fun getDragProgress(viewLeft: Int, maxDrag: Int): Float {
            return Math.abs(viewLeft).toFloat() / maxDrag
        }

        override fun clampViewPosition(left: Int, maxDrag: Int): Int {
            return Math.max(-maxDrag, Math.min(left, 0))
        }

        override fun enableEdgeTrackingOn(dragHelper: ViewDragHelper) {
            dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT)
        }
    }
}
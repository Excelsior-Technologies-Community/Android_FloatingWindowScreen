package com.ext.floatingwindow.core

import android.content.Context
import android.graphics.PixelFormat
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.ext.floatingwindow.ui.DragTouchListener
import com.ext.floatingwindow.ui.FloatingLayout

class FloatingWindowManager(private val context: Context) {

    private val windowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private var floatingView: View? = null

    fun show(view: View, config: FloatingWindowConfig) {

        if (!Settings.canDrawOverlays(context)) {
            throw IllegalStateException("Overlay permission not granted")
        }

        if (floatingView != null) return

        val params = WindowManager.LayoutParams(
            config.width,
            config.height,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            if (config.focusable)
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            else
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.START
        params.x = config.x
        params.y = config.y

        // ✅ 1️⃣ Attach close listener BEFORE adding view
        if (view is FloatingLayout) {
            view.setOnCloseClickListener {
                remove()
            }
        }

        // ✅ 2️⃣ Attach drag listener
        if (config.draggable) {
            view.setOnTouchListener(
                DragTouchListener(params, windowManager)
            )
        }

        // ✅ 3️⃣ Finally add view to WindowManager
        floatingView = view
        windowManager.addView(view, params)
    }


    fun remove() {
        floatingView?.let {
            windowManager.removeView(it)
        }
        floatingView = null
    }

    fun isShowing(): Boolean = floatingView != null
}

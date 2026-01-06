package com.ext.floatingwindow.core

import android.content.Context
import android.graphics.PixelFormat
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.ext.floatingwindow.ui.DragTouchListener

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

        if (config.draggable) {
            view.setOnTouchListener(
                DragTouchListener(params, windowManager)
            )
        }

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

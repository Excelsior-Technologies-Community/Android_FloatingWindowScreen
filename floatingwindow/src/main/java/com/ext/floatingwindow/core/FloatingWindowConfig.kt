package com.ext.floatingwindow.core

import android.view.WindowManager

data class FloatingWindowConfig(
    val width: Int = WindowManager.LayoutParams.WRAP_CONTENT,
    val height: Int = WindowManager.LayoutParams.WRAP_CONTENT,
    val x: Int = 0,
    val y: Int = 100,
    val draggable: Boolean = true,
    val focusable: Boolean = false
)

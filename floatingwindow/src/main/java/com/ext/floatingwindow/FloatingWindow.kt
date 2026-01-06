package com.ext.floatingwindow

import android.content.Context
import android.view.View
import com.ext.floatingwindow.core.FloatingWindowConfig
import com.ext.floatingwindow.core.FloatingWindowManager

class FloatingWindow(context: Context) {

    private val manager = FloatingWindowManager(context)

    fun show(view: View, config: FloatingWindowConfig) {
        manager.show(view, config)
    }

    fun show(view: View) {
        manager.show(view)
    }

    fun dismiss() {
        manager.remove()
    }
}

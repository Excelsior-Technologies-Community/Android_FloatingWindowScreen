package com.ext.android_floatingwindowscreen

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ext.floatingwindow.FloatingWindow
import com.ext.floatingwindow.core.FloatingWindowConfig
import com.ext.floatingwindow.permission.OverlayPermissionHelper

class MainActivity : AppCompatActivity() {
    private var floatingWindow: FloatingWindow? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.btnShow).setOnClickListener {
            showFloatingWindow()
        }

        findViewById<Button>(R.id.btnRemove).setOnClickListener {
            floatingWindow?.dismiss()
        }
    }
    private fun showFloatingWindow() {

        // 1️⃣ Check overlay permission
        if (!OverlayPermissionHelper.canDrawOverlays(this)) {
            OverlayPermissionHelper.requestPermission(this)
            return
        }

        // 2️⃣ Inflate floating view from app module
        val floatingView = layoutInflater.inflate(
            R.layout.view_floating_test,
            null
        )

        // 3️⃣ Create floating window
        floatingWindow = FloatingWindow(this)

        floatingWindow?.show(
            floatingView,
            FloatingWindowConfig(
                width = 500,
                height = 300,
                x = 100,
                y = 200,
                draggable = true
            )
        )
    }
}
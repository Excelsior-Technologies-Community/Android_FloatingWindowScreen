package com.ext.floatingwindow.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.ext.floatingwindow.core.FloatingWindowManager

class FloatingWindowService : Service() {

    private lateinit var manager: FloatingWindowManager

    override fun onCreate() {
        super.onCreate()
        manager = FloatingWindowManager(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Floating view will be attached externally
        return START_STICKY
    }

    override fun onDestroy() {
        manager.remove()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

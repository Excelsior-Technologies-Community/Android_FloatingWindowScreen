package com.ext.floatingwindow.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.ext.floatingwindow.R

class FloatingLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var contentContainer: FrameLayout? = null
    private var closeButton: ImageView? = null
    private var onCloseClick: (() -> Unit)? = null

    var draggable = true
    var startX = 0
    var startY = 200


    /** 🔥 VERY IMPORTANT FLAG */
    private var internalInflation = true

    init {
        // Inflate internal layout FIRST
        LayoutInflater.from(context)
            .inflate(R.layout.layout_floating_container, this, true)

        contentContainer = findViewById(R.id.contentContainer)
        closeButton = findViewById(R.id.btnClose)

        internalInflation = false   // ✅ now safe to intercept addView

        closeButton?.bringToFront()

        // XML attributes
        val ta = context.obtainStyledAttributes(
            attrs,
            R.styleable.FloatingWindowView
        )

        val showClose = ta.getBoolean(
            R.styleable.FloatingWindowView_fw_showClose,
            true
        )

        draggable = ta.getBoolean(
            R.styleable.FloatingWindowView_fw_draggable,
            true
        )

        startX = ta.getInt(
            R.styleable.FloatingWindowView_fw_startX,
            0
        )

        startY = ta.getInt(
            R.styleable.FloatingWindowView_fw_startY,
            200
        )

        closeButton?.visibility = if (showClose) VISIBLE else GONE
        ta.recycle()
    }

    // ✅ SAFE OVERRIDE
    override fun addView(
        child: View,
        index: Int,
        params: ViewGroup.LayoutParams
    ) {
        // During internal inflation → behave normally
        if (internalInflation || contentContainer == null) {
            super.addView(child, index, params)
            return
        }

        // Allow internal views
        if (child.id == R.id.contentContainer || child.id == R.id.btnClose) {
            super.addView(child, index, params)
        } else {
            // Redirect user content
            contentContainer?.addView(child, params)
        }
    }

    fun setOnCloseClickListener(listener: () -> Unit) {
        onCloseClick = listener
        closeButton?.setOnClickListener {
            onCloseClick?.invoke()
        }
    }
}

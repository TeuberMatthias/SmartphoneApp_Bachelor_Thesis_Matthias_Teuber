package com.example.pininterface.Interface

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.widget.Button

interface InterfaceTouchListener {

    @SuppressLint("ClickableViewAccessibility")
    fun setOnTouchListenerButtonList(pListButton: MutableList<Button>, pColorHighlighted: Int, pColorNormal: Int) {
        pListButton.forEachIndexed { _, button ->
            button.setOnTouchListener { _, eventTest ->
                when (eventTest.action) {
                    MotionEvent.ACTION_DOWN -> {
                        buttonPressed(button, pColorHighlighted)
                        false
                    }

                    MotionEvent.ACTION_UP -> {
                        buttonReleased(button, pColorNormal)
                        false
                    }

                    else -> false
                }
            }
        }
    }

    fun buttonPressed (pButton: Button, pColorHighlighted: Int)
    fun buttonReleased (pButton: Button, pColorNormal: Int)
}
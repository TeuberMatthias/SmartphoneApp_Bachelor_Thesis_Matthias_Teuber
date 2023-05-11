package com.example.pininterface.interfaces

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.widget.Button

/**
 * Interface to setup a touch listener
 */
interface InterfaceTouchListener {

    /**
     * Initiates TouchListener for a list of Buttons to change their color when pressed down/released
     * @param pListButton list of Buttons
     * @param pColorHighlighted color as Int
     * @param pColorNormal color as Int
     */
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

    /**
     * Button is pressed down
     * @param pButton Button
     * @param pColorHighlighted color as Int
     */
    fun buttonPressed (pButton: Button, pColorHighlighted: Int)

    /**
     * Button is released
     * @param pButton Button
     * @param pColorNormal color as int
     */
    fun buttonReleased (pButton: Button, pColorNormal: Int)
}
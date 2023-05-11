package com.example.pininterface.interfaces

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Interface to manage the VisualAid TextView
 */
interface InterfaceLayoutVisualAidActivity {

    /**
     * Makes the VisualAid TextView visible and moves it to the Position of pButton
     * (binds VisAid it to start, end and top of the button)
     * @param pVisAid VisualAid TextView
     * @param pButton Button to which the VisualAid should be moved
     * @param pLayoutParamsVisAid LayoutParams of the VisualAid
     */
    fun setVisualAidVisible(pVisAid: TextView, pButton: Button, pLayoutParamsVisAid: ConstraintLayout.LayoutParams) {

        pVisAid.visibility = View.VISIBLE
        pVisAid.translationZ = 90.0F
        pVisAid.text = pButton.tag.toString()
        pLayoutParamsVisAid.bottomToTop = pButton.id
        pLayoutParamsVisAid.endToEnd = pButton.id
        pLayoutParamsVisAid.startToStart = pButton.id
        pVisAid.layoutParams = pLayoutParamsVisAid
    }

    /**
     * Makes the VisualAid invisible after a delay
     * @param pVisAid VisualAid TextView
     * @param pDelay time in msec until the VisAid should become invisible
     */
    fun setVisualAidInvisible(pVisAid: TextView, pDelay: Int) {

        Executors.newSingleThreadScheduledExecutor().schedule({
            pVisAid.visibility = View.INVISIBLE
        }, pDelay.toLong(), TimeUnit.MILLISECONDS)
    }

    /**
     * Initiates TouchListener for a list of Buttons
     * Makes VisAid visible when pressed down, and invisible when released
     * Also calls for each instance a later defined function
     * @param pListButtons list of Buttons as MutableList<Button>
     * @param pVisAid Visual Aid TextView
     * @param pLayoutParamsVisAid LayoutParams of the Visual Aid
     */
    @SuppressLint("ClickableViewAccessibility")
    fun buttonListenersVisualAid(pListButtons: MutableList<Button>, pVisAid: TextView, pLayoutParamsVisAid: ConstraintLayout.LayoutParams) {

        pListButtons.forEachIndexed { _, button ->
            button.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        setVisualAidVisible(pVisAid, button, pLayoutParamsVisAid)
                        buttonPressedVisAid(button)
                        false
                    }
                    MotionEvent.ACTION_UP -> {
                        setVisualAidInvisible(pVisAid, 100)
                        buttonReleasedVisAid(button)
                        false
                    } else -> false
                }
            }
        }
    }

    /**
     * Function for when Button pressed down
     * @param pButton Button
     */
    fun buttonPressedVisAid(pButton: Button)

    /**
     * Function for when Button is released
     * @param pButton Button
     */
    fun buttonReleasedVisAid(pButton: Button)

}
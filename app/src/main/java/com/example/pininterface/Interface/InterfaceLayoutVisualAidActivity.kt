package com.example.pininterface.Interface

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

interface InterfaceLayoutVisualAidActivity {

    fun setVisualAidVisible(view: TextView, button: Button, layoutParamsVisAid: ConstraintLayout.LayoutParams) {
        view.visibility = View.VISIBLE
        view.translationZ = 90.0F
        view.text = button.tag.toString()
        layoutParamsVisAid.bottomToTop = button.id
        layoutParamsVisAid.endToEnd = button.id
        layoutParamsVisAid.startToStart = button.id
        view.layoutParams = layoutParamsVisAid
    }

    fun setVisualAidInvisible(view: TextView, delay: Int) {
        Executors.newSingleThreadScheduledExecutor().schedule({
            view.visibility = View.INVISIBLE
        }, delay.toLong(), TimeUnit.MILLISECONDS)
    }

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

    fun buttonPressedVisAid(pButton: Button)
    fun buttonReleasedVisAid(pButton: Button)

}
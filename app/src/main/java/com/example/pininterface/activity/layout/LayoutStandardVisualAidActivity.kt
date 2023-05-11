package com.example.pininterface.activity.layout

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.pininterface.Interface.InterfaceLayoutVisualAidActivity
import com.example.pininterface.R
import com.example.pininterface.enums.EnumInterfaceTypes


class LayoutStandardVisualAidActivity : LayoutStandardActivity(), InterfaceLayoutVisualAidActivity {
    private lateinit var visAid: TextView
    private lateinit var layoutParamsVisAid: ConstraintLayout.LayoutParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setInterfaceTypeForLayout(EnumInterfaceTypes.STANDARD_VIS)
        Log.e("InterfaceLayout", interfaceType.toString())

        colorNumHighlighted = ContextCompat.getColor(this, R.color.button_visual_aid_highlighted)

        visAid = binding.visAid
        layoutParamsVisAid = visAid.layoutParams as ConstraintLayout.LayoutParams

        val allButtons: MutableList<Button> = listNumButtons.toMutableList()
        allButtons.addAll(listCtrlButtons.toMutableList())

        buttonListenersVisualAid(allButtons, visAid, layoutParamsVisAid)
    }

    override fun buttonPressedVisAid(pButton: Button) {
        changeShape(pButton, shapeRoundedSquareButton)
        setColorHighlightAndObscureButtonList(listCtrlButtons, pButton, colorControlHighlighted, colorControlDeactivated)
        setColorHighlightAndObscureButtonList(listNumButtons, pButton, colorNumHighlighted, colorNumDeactivated)
    }

    override fun buttonReleasedVisAid(pButton: Button) {
        changeShape(pButton, shapeRoundButton)
        setColorButtonList(listNumButtons, colorNumNormal)
        setColorButtonList(listCtrlButtons, colorControlNormal)
    }

}
package com.example.pininterface.Interface

import android.content.res.ColorStateList
import android.widget.Button
import android.widget.TextView

interface InterfaceViewManipulation {
    fun setColorHighlightAndObscureButtonList(pButtonList: MutableList<Button>, pActiveButton: Button, pActiveButtonColor: Int, pOtherButtonColor: Int) {
        pButtonList.forEach{
            if (it != pActiveButton)
                setColorButton(it, pOtherButtonColor)
            else
                setColorButton(it, pActiveButtonColor)
        }
    }

    fun setColorButtonList (pButtonList: MutableList<Button>, pColor: Int) {
        pButtonList.forEach {
            setColorButton(it, pColor)
        }
    }

    fun setColorButton(pButton: Button, pColor: Int) {
        pButton.backgroundTintList = ColorStateList.valueOf(pColor)
    }


    fun updateTextView(view: TextView, text: String) {
        view.text = text
    }

    fun hidePinString(pString: String): String {
        return pString.replace("\\d".toRegex(), "\u2022")
    }
}
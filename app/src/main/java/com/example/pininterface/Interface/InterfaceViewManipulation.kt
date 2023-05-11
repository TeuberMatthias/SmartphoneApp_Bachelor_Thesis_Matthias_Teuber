package com.example.pininterface.Interface

import android.content.res.ColorStateList
import android.widget.Button
import android.widget.TextView

/**
 * Interface with some common functions to manipulate some View elements
 */
interface InterfaceViewManipulation {

    /**
     * Changes the color (backgroundTintList) of a list of Buttons,
     * also changes the color of a specific Button from that list to another color
     * @param pButtonList list of Buttons
     * @param pActiveButton specific Button which should get another color
     * @param pActiveButtonColor color the specific button should get as Int
     * @param pOtherButtonColor color the other buttons should get as Int
     */
    fun setColorHighlightAndObscureButtonList(pButtonList: MutableList<Button>, pActiveButton: Button, pActiveButtonColor: Int, pOtherButtonColor: Int) {

        pButtonList.forEach{
            if (it != pActiveButton)
                setColorButton(it, pOtherButtonColor)
            else
                setColorButton(it, pActiveButtonColor)
        }
    }

    /**
     * Changes the color (backgroundTintList) of a list of Buttons
     * @param pButtonList list of Buttons
     * @param pColor new color the Buttons should get as Int
     */
    fun setColorButtonList (pButtonList: MutableList<Button>, pColor: Int) {

        pButtonList.forEach {
            setColorButton(it, pColor)
        }
    }

    /**
     * Changes the color of a single Button
     * @param pButton Button
     * @param pColor new color as Int
     */
    fun setColorButton(pButton: Button, pColor: Int) {

        pButton.backgroundTintList = ColorStateList.valueOf(pColor)
    }

    /**
     * Update the text of a TextView
     * @param pView TextView
     * @param pText new Text as String
     */
    fun updateTextView(pView: TextView, pText: String) {

        pView.text = pText
    }

    /**
     * Replaces all Characters of a String with "•"
     * @param pString String
     * @return new String of the same length but consists only of "•"
     */
    fun hidePinString(pString: String): String {

        return pString.replace("\\d".toRegex(), "\u2022")
    }
}
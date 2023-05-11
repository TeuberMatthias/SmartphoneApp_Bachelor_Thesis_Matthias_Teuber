package com.example.pininterface.interfaces

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
     * @param pActiveButtonColor color the specific button that should be highlighted as Int
     * @param pOtherButtonColor color the other buttons as Int
     * @param pActiveButtonColorText color of the Text of the active Button
     * @param pOtherButtonColorText color of the text of the other Buttons
     */
    fun setColorHighlightAndObscureButtonList(pButtonList: MutableList<Button>, pActiveButton: Button, pActiveButtonColor: Int, pOtherButtonColor: Int, pActiveButtonColorText: Int, pOtherButtonColorText: Int) {

        pButtonList.forEach{
            if (it != pActiveButton) {
                setColorButton(it, pOtherButtonColor)
                setColorButtonText(it, pOtherButtonColorText)
            } else {
                setColorButton(it, pActiveButtonColor)
                setColorButtonText(it, pActiveButtonColorText)
            }
        }
    }

    /**
     * Changes the color (backgroundTintList) for a list of Buttons
     * @param pListButton list of Buttons
     * @param pColor new color the Buttons should get as Int
     */
    fun setColorButtonList (pListButton: MutableList<Button>, pColor: Int) {

        pListButton.forEach {
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
     * Changes the Text color for a list of Buttons
     * @param pListButton list of Buttons
     * @param pColor new color the Buttons should get as Int
     */
    fun setColorTextButtonList(pListButton: MutableList<Button>, pColor: Int) {

        pListButton.forEach {
            setColorButtonText(it, pColor)
        }
    }

    /**
     * Changes the color of the text of a Button
     * @param pButton Button
     * @param pColor new Color as Int
     */
    fun setColorButtonText(pButton: Button, pColor: Int) {

        pButton.setTextColor(pColor)
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
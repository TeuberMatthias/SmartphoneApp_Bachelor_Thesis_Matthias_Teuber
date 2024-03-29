package com.example.pininterface.activity.layout

import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.pininterface.R
import com.example.pininterface.interfaces.InterfaceTouchListener
import com.example.pininterface.activity.helper.SuperLayoutActivity
import com.example.pininterface.databinding.ActivityLayoutInterfaceStandardBinding
import com.example.pininterface.enums.EnumInterfaceTypes

/**
 * Standard Layout
 */
open class LayoutStandardActivity : SuperLayoutActivity(), InterfaceTouchListener {

    lateinit var binding: ActivityLayoutInterfaceStandardBinding

    protected var shapeRoundedSquareButton: Int = 0
    protected var shapeRoundButton: Int = 0

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setInterfaceTypeForLayout(EnumInterfaceTypes.STANDARD)
        Log.e("InterfaceLayout", interfaceType.toString())

        setContentView(R.layout.activity_layout_interface_standard)

        binding = ActivityLayoutInterfaceStandardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pinTargetTextView = binding.header.pinTarget
        pinSubmissionTextView = binding.header.pinSubmission

        button0 = binding.button0
        button1 = binding.button1
        button2 = binding.button2
        button3 = binding.button3
        button4 = binding.button4
        button5 = binding.button5
        button6 = binding.button6
        button7 = binding.button7
        button8 = binding.button8
        button9 = binding.button9
        listNumButtons.addAll(listOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9))

        buttonDel = binding.buttonDel
        buttonAccept = binding.buttonAccept
        listCtrlButtons.addAll(listOf(buttonDel, buttonAccept))

        buttonEmergency = binding.emergency.emergencyButton

        shapeRoundedSquareButton = R.drawable.rounded_square_button
        shapeRoundButton = R.drawable.round_button

        updateTextView(pinSubmissionTextView, participant.getActivePin().getPinSubmission())
        updateTextView(pinTargetTextView, participant.getActivePin().getPin())

        buttonListeners()
        setOnTouchListenerButtonList(listNumButtons, colorNumHighlighted, colorNumNormal)
        setOnTouchListenerButtonList(listCtrlButtons, colorControlHighlighted, colorControlNormal)
    }

    /**
     * When Button pressed down
     * Changes shape of Button
     * Highlights Button
     * @param pButton Button
     * @param pColorHighlighted highlight Color as Int (background resource value)
     */
    override fun buttonPressed(pButton: Button, pColorHighlighted: Int) {

        setColorButton(pButton, pColorHighlighted)
        changeShape(pButton, shapeRoundedSquareButton)
    }

    /**
     * When Button released
     * Changes shape of Button back to normal
     * Changes color of Button back to normal
     * @param pButton Button
     * @param pColorNormal normal Color of Button as Int (background resource value)
     */
    override fun buttonReleased(pButton: Button, pColorNormal: Int) {

        setColorButton(pButton, pColorNormal)
        changeShape(pButton, shapeRoundButton)
    }

    /**
     * changes shape of a Button
     * @param pButton Button
     * @param pShape the shape as Int (background resource value)
     */
    fun changeShape(pButton: Button, pShape: Int) {

        pButton.setBackgroundResource(pShape)
    }
}
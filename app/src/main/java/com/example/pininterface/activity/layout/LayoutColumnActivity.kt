package com.example.pininterface.activity.layout

import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.pininterface.R
import com.example.pininterface.interfaces.InterfaceTouchListener
import com.example.pininterface.activity.helper.SuperLayoutActivity
import com.example.pininterface.databinding.ActivityLayoutInterfaceColumnBinding
import com.example.pininterface.enums.EnumInterfaceTypes

/**
 * Column Layout
 */
open class LayoutColumnActivity : SuperLayoutActivity(), InterfaceTouchListener {

    protected lateinit var binding: ActivityLayoutInterfaceColumnBinding

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setInterfaceTypeForLayout(EnumInterfaceTypes.COLUMN)
        Log.e("InterfaceLayout", interfaceType.toString())

        setContentView(R.layout.activity_layout_interface_column)

        binding = ActivityLayoutInterfaceColumnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pinTargetTextView = binding.headerColumn.pinTarget
        pinSubmissionTextView = binding.headerColumn.pinSubmission

        button0 = binding.buttonColumn0
        button1 = binding.buttonColumn1
        button2 = binding.buttonColumn2
        button3 = binding.buttonColumn3
        button4 = binding.buttonColumn4
        button5 = binding.buttonColumn5
        button6 = binding.buttonColumn6
        button7 = binding.buttonColumn7
        button8 = binding.buttonColumn8
        button9 = binding.buttonColumn9
        listNumButtons.addAll(listOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9))

        buttonDel = binding.buttonColumnDel
        buttonAccept = binding.buttonColumnAccept
        listCtrlButtons.addAll(listOf(buttonDel, buttonAccept))

        buttonEmergency = binding.emergency.emergencyButton


        updateTextView(pinSubmissionTextView, participant.getActivePin().getPinSubmission())
        updateTextView(pinTargetTextView, participant.getActivePin().getPin())

        buttonListeners()
        setOnTouchListenerButtonList(listNumButtons, colorNumHighlighted, colorNumNormal)
        setOnTouchListenerButtonList(listCtrlButtons, colorControlHighlighted, colorControlNormal)
    }

    /**
     * When Button pressed down
     * Highlights Button
     * @param pButton Button
     * @param pColorHighlighted highlight Color as Int (background resource value)
     */
    override fun buttonPressed(pButton: Button, pColorHighlighted: Int) {

        setColorButton(pButton, pColorHighlighted)
    }

    /**
     * When Button released
     * Changes color of Button back to normal
     * @param pButton Button
     * @param pColorNormal normal Color of Button as Int (background resource value)
     */
    override fun buttonReleased(pButton: Button, pColorNormal: Int) {

        setColorButton(pButton, pColorNormal)
    }
}
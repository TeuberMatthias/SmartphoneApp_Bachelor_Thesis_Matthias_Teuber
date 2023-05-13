package com.example.pininterface.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.pininterface.R
import com.example.pininterface.activity.helper.SuperActivityNavigation
import com.example.pininterface.activity.layout.LayoutColumnActivity
import com.example.pininterface.activity.layout.LayoutColumnVisualAidActivity
import com.example.pininterface.activity.layout.LayoutStandardActivity
import com.example.pininterface.activity.layout.LayoutStandardVisualAidActivity
import com.example.pininterface.databinding.ActivityIntermediatePageBinding
import com.example.pininterface.enums.EnumInterfaceTypes
import com.example.pininterface.interfaces.InterfaceGson
import com.example.pininterface.interfaces.InterfaceViewManipulation
import com.example.pininterface.logic.Participant

/**
 * Intermediate Page
 * Starts the next Interface Activity or if no next Interface activity goes to DemographicsActivity
 * Will be displayed before each Interface Activity and after all Interfaces are solved
 * Informs the User about the next Interface and when to put on/take off the gloves
 */
class IntermediatePageActivity : SuperActivityNavigation(), InterfaceGson, InterfaceViewManipulation {

    private lateinit var participant: Participant
    private lateinit var binding: ActivityIntermediatePageBinding

    private lateinit var textViewMessageInterface: TextView
    private lateinit var textViewMessageGloves: TextView
    private lateinit var buttonContinue: Button

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intermediate_page)

        binding = ActivityIntermediatePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        textViewMessageInterface = binding.textViewMessageInterface
        textViewMessageGloves = binding.textViewMessageGloves
        buttonContinue = binding.buttonContinueIntermediatePage

        val intent = intent
        participant = getParticipantFromJson(intent)

        writeMessage()

        buttonContinue.setOnClickListener { nextInterfaceActivity() }
    }

    /**
     * Writes the messages on the Intermediate page
     * When the first Interface started, it will inform the user to put on the gloves
     * When the last Interface is finished, it will inform the user to take of the gloves
     * It will inform the user what the next Interface will be or if all interfaces are finished.
     */
    private fun writeMessage() {

        val messageInterface = if (participant.getActiveInterface() != EnumInterfaceTypes.NONE)
            getString(R.string.next_interface) + "\n" + this.resources.getString(participant.getActiveInterface().stringResId)
        else {
            getString(R.string.all_pin_interfaces_solved)
        }

        val messageGloves = if (participant.getActiveInterface() == EnumInterfaceTypes.NONE)
            getString(R.string.message_take_of_gloves)
        else if (participant.getUsedInterfaces().size == 1)
            getString(R.string.message_put_on_gloves)
        else
            ""

        updateTextView(textViewMessageInterface, messageInterface)
        updateTextView(textViewMessageGloves, messageGloves)
    }

    /**
     * Goes to the Next Interface Activity, depending of the active Interface of the current Participant.
     * If no next Interface Activity it will go to DemographicActivity
     */
    private fun nextInterfaceActivity() {

        when (participant.getActiveInterface()) {
            EnumInterfaceTypes.STANDARD     -> startNewActivity(participant, LayoutStandardActivity::class.java)
            EnumInterfaceTypes.COLUMN       -> startNewActivity(participant, LayoutColumnActivity::class.java)
            EnumInterfaceTypes.STANDARD_VIS -> startNewActivity(participant, LayoutStandardVisualAidActivity::class.java)
            EnumInterfaceTypes.COLUMN_VIS   -> startNewActivity(participant, LayoutColumnVisualAidActivity::class.java)
            else                            -> startNewActivity(participant, DemographicActivity::class.java)
        }
    }
}
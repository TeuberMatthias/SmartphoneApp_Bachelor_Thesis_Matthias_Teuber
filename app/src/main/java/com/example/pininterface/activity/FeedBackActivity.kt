package com.example.pininterface.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.pininterface.database.interfaces.InterfaceDbFeedback
import com.example.pininterface.database.interfaces.InterfaceDbParticipant
import com.example.pininterface.interfaces.InterfaceGson
import com.example.pininterface.R
import com.example.pininterface.databinding.ActivityLayoutFeedbackBinding
import com.example.pininterface.activity.helper.SuperActivityNavigation
import com.example.pininterface.logic.Participant

/**
 * Activity Feedback
 */
class FeedBackActivity : SuperActivityNavigation(), InterfaceGson, InterfaceDbParticipant, InterfaceDbFeedback {

    private lateinit var participant: Participant

    private lateinit var binding: ActivityLayoutFeedbackBinding
    private lateinit var buttonContinue: Button
    private lateinit var editTextFeedBack: EditText

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_feedback)

        binding = ActivityLayoutFeedbackBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = intent
        participant = getParticipantFromJson(intent)

        editTextFeedBack = binding.editTextFeedback
        buttonContinue = binding.buttonFeedbackContinue.buttonContinue
        buttonContinue.setOnClickListener { finishParticipant() }

        buttonContinue.text = getString(R.string.finish)
    }

    /**
     * Saves the feedback in DB and sets complete field in DB for this participant to 1 (true)
     * Then goes back to the main activity where a new participant ID is generated
     */
    private fun finishParticipant() {

        dbAddFeedback(participant.getPhoneID(), participant.getID(), editTextFeedBack.text.toString(), this)
        dbUpdateParticipantComplete(participant.getPhoneID(), participant.getID(), 1, this)

        startNewActivity(participant, StartActivity::class.java)
    }





}
package com.example.pininterface.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.pininterface.Interface.InterfaceDbFeedback
import com.example.pininterface.Interface.InterfaceDbParticipant
import com.example.pininterface.Interface.InterfaceGson
import com.example.pininterface.R
import com.example.pininterface.databinding.ActivityLayoutFeedbackBinding
import com.example.pininterface.helper.SuperActivityNavigation
import com.example.pininterface.logic.Participant

class FeedBackActivity : SuperActivityNavigation(), InterfaceGson, InterfaceDbParticipant, InterfaceDbFeedback {
    private lateinit var participant: Participant

    private lateinit var binding: ActivityLayoutFeedbackBinding
    private lateinit var buttonContinue: Button
    private lateinit var editTextFeedBack: EditText

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
        buttonContinue.setOnClickListener { finishActivity() }
    }

    fun finishActivity() {
        val feedBack = editTextFeedBack.text.toString()
        participant.setFeedBack(feedBack)
        dbAddRecordFeedBack(participant.getID(), editTextFeedBack.text.toString(), this)
        dbUpdateParticipantComplete(participant.getID(), 1, this)
        startNewActivity(participant, MainActivity::class.java) //TODO DATABANK
    }





}
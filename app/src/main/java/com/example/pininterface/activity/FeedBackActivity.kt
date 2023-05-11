package com.example.pininterface.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.pininterface.Interface.InterfaceGson
import com.example.pininterface.R
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.ModelClassFeedBack
import com.example.pininterface.database.ModelClassParticipant
import com.example.pininterface.databinding.ActivityLayoutFeedbackBinding
import com.example.pininterface.helper.SuperActivityNavigation
import com.example.pininterface.logic.Participant

class FeedBackActivity : SuperActivityNavigation(), InterfaceGson {
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
        addRecordFeedBack()
        updateParticipantComplete()
        startNewActivity(participant, MainActivity::class.java) //TODO DATABANK
    }

    private fun addRecordFeedBack() {
        val id = participant.getID()
        val feedback = editTextFeedBack.text.toString()

        val dataBaseHelper: DataBaseHelper = DataBaseHelper(this)
        Log.e("dataBase", dataBaseHelper.addFeedBack(ModelClassFeedBack(id, feedback)).toString())
    }

    private fun updateParticipantComplete() {
        val id = participant.getID()
        val complete: Int = 1

        val dataBaseHelper: DataBaseHelper = DataBaseHelper(this)
        Log.e("db_update_participant", dataBaseHelper.updateParticipantComplete(ModelClassParticipant(id, complete)).toString())
    }

}
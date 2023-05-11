package com.example.pininterface.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import com.example.pininterface.R
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.ModelClassDemographics
import com.example.pininterface.databinding.ActivityDemographicsBinding
import com.example.pininterface.logic.Demographics
import com.example.pininterface.helper.SuperActivityNavigation
import com.example.pininterface.logic.Participant
import com.google.gson.Gson

class DemographicActivity : SuperActivityNavigation() {
    private lateinit var participant: Participant
    private lateinit var binding: ActivityDemographicsBinding
    lateinit var buttonContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demographics)

        binding = ActivityDemographicsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = intent
        val gson = Gson()
        participant = gson.fromJson<Participant>(intent.getStringExtra("participant"), Participant::class.java)

        buttonContinue = binding.demographicsContinue.buttonContinue
        buttonContinue.setOnClickListener { continueButtonPressed() }

    }

    fun continueButtonPressed() {
        val radioGroupGenderValue = binding.radioGroupGender.checkedRadioButtonId
        val radioGroupDominantHand = binding.radioGroupId.checkedRadioButtonId
        val age: Int = binding.ageInput.text.toString().toIntOrNull() ?: -1

        if (radioGroupGenderValue <= 0) {
            Toast.makeText(this, "No Gender Selected", Toast.LENGTH_LONG).show()
            return
        } else if (age < 13 || age > 120) {
            Toast.makeText(this, "Invalid Age Selected", Toast.LENGTH_LONG).show()
            return
        } else if (radioGroupDominantHand <= 0) {
            Toast.makeText(this, "No Dominant Hand Selected", Toast.LENGTH_LONG).show()
            return
        } else {
            val gender: RadioButton = findViewById(radioGroupGenderValue)
            val dominantHand: RadioButton = findViewById(radioGroupDominantHand)
            val demographics = Demographics(age, dominantHand.tag.toString(), gender.tag.toString())
            Log.e("results", demographics.getDemographicsString())
            participant.setDemographics(demographics)

            val id = participant.getID()
            val dataBaseHelper: DataBaseHelper = DataBaseHelper(this)
            val modelClassDemographics: ModelClassDemographics = ModelClassDemographics(id, age, gender.tag.toString(), dominantHand.tag.toString())
            Log.e("Demograpics DB", dataBaseHelper.addDemographics(modelClassDemographics).toString())
            startNewActivity(participant, SystemUsabilityScaleActivity::class.java)
        }
    }

}
package com.example.pininterface.activity

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import com.example.pininterface.R
import com.example.pininterface.database.interfaces.InterfaceDbDemographics
import com.example.pininterface.databinding.ActivityDemographicsBinding
import com.example.pininterface.activity.helper.SuperActivityNavigation
import com.example.pininterface.interfaces.InterfaceGson
import com.example.pininterface.logic.Participant

/**
 * Activity Demographic
 * Validate and save the user demographics
 */
class DemographicActivity : SuperActivityNavigation(), InterfaceDbDemographics, InterfaceGson {

    private lateinit var participant: Participant
    private lateinit var binding: ActivityDemographicsBinding
    private lateinit var buttonContinue: Button
    private var toastDemographics: Toast? = null

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demographics)

        binding = ActivityDemographicsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        buttonContinue = binding.demographicsContinue.buttonContinue

        val intent = intent
        participant = getParticipantFromJson(intent)

        buttonContinue.setOnClickListener { continueButtonPressed() }
    }

    /**
     * buttonContinue Pressed
     * Checks if the submitted demographics are valid and complete
     * Goes to the next activity (SystemUsabilityScaleActivity) if yes,
     * otherwise displays toast with error message
     */
    private fun continueButtonPressed() {

        val radioGroupGenderValue = binding.radioGroupGender.checkedRadioButtonId
        val radioGroupDominantHand = binding.radioGroupDominantHand.checkedRadioButtonId

        val age: Int = binding.ageInput.text.toString().toIntOrNull() ?: -1

        if (radioGroupGenderValue <= 0) {
            toastDemographics = Toast.makeText(this, getString(R.string.no_gender_selected), Toast.LENGTH_LONG)
            toastDemographics?.show()
            return
        } else if (age < 3 || age > 120) {
            toastDemographics = Toast.makeText(this, getString(R.string.invalid_age_selected), Toast.LENGTH_LONG)
            toastDemographics?.show()
            return
        } else if (radioGroupDominantHand <= 0) {
            toastDemographics = Toast.makeText(this, getString(R.string.no_dominant_hand_selected), Toast.LENGTH_LONG)
            toastDemographics?.show()
            return
        } else {
            toastDemographics?.cancel()

            val gender: RadioButton = findViewById(radioGroupGenderValue)
            val dominantHand: RadioButton = findViewById(radioGroupDominantHand)
            val id = participant.getID()

            dbAddDemographics(id, age, gender.tag.toString(), dominantHand.tag.toString(), this)

            startNewActivity(participant, SystemUsabilityScaleActivity::class.java)
        }
    }

    //TODO: maybe add a "any mobility impairements" or something like that question?
}
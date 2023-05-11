package com.example.pininterface.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.pininterface.Interface.InterfaceGson
import com.example.pininterface.Interface.InterfaceViewManipulation
import com.example.pininterface.R
import com.example.pininterface.database.interfaces.InterfaceDbSUS
import com.example.pininterface.databinding.ActivitySystemUsabilityScaleBinding
import com.example.pininterface.databinding.ModSusScaleBinding
import com.example.pininterface.enums.EnumInterfaceTypes
import com.example.pininterface.activity.helper.SuperActivityNavigation
import com.example.pininterface.logic.Participant

class SystemUsabilityScaleActivity : SuperActivityNavigation(), InterfaceViewManipulation, InterfaceGson, InterfaceDbSUS {
    private lateinit var participant: Participant
    private lateinit var binding: ActivitySystemUsabilityScaleBinding

    private lateinit var activeInterfaceTyp: EnumInterfaceTypes
    private var listUsedInterfaceTypes = mutableListOf<EnumInterfaceTypes>()
    private var page: Int = 0

    private lateinit var buttonContinue: Button
    private lateinit var buttonNext: Button
    private lateinit var buttonBack: Button
    private lateinit var textViewInterfaceTyp: TextView
    private lateinit var textViewPageIndicator: TextView

    private lateinit var listQuestionView: MutableList<ModSusScaleBinding>
    private lateinit var listQuestionText: MutableList<String>

    private var listAnswers = initListAnswers()

    private var colorButtonInactive: Int = 0
    private var colorButtonActive: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_usability_scale)

        binding = ActivitySystemUsabilityScaleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        buttonBack = binding.buttonBack
        buttonNext = binding.buttonNext
        buttonContinue = binding.systemUsabilityScaleContinue.buttonContinue
        textViewInterfaceTyp = binding.interfaceTyp
        textViewPageIndicator = binding.textViewPageIndicator

        val question0 = binding.question0
        val question1 = binding.question1
        val question2 = binding.question2
        val question3 = binding.question3
        val question4 = binding.question4
        listQuestionView = mutableListOf(question0, question1, question2, question3, question4)

        listQuestionText = mutableListOf<String>(getString(R.string.sus_00), getString(R.string.sus_01),
            getString(R.string.sus_02), getString(R.string.sus_03), getString(R.string.sus_04),
            getString(R.string.sus_05), getString(R.string.sus_06), getString(R.string.sus_07),
            getString(R.string.sus_08), getString(R.string.sus_09))

        colorButtonInactive = ContextCompat.getColor(this, R.color.button_inactive)
        colorButtonActive = ContextCompat.getColor(this, R.color.button_active)

        val intent = intent
        participant = getParticipantFromJson(intent)

        listUsedInterfaceTypes = participant.getUsedInterfaces()
        if (listUsedInterfaceTypes.isEmpty()) {
            Log.e("listUsedInterfaceTypes", "list is empty")
            //TODO: go next activity
        }
        activeInterfaceTyp = listUsedInterfaceTypes.removeFirst()

        buttonContinue.setOnClickListener { continueButtonPressed() }

        buttonNext.setOnClickListener { nextButtonPressed() }
        buttonBack.setOnClickListener { backButtonPressed() }

        writeQuestions()
        updateTextView(textViewInterfaceTyp, activeInterfaceTyp.value)
        updateButtonsPage0()

    }

    private fun initListAnswers(): MutableList<Int> {
        return mutableListOf<Int>(-1, -1, -1, -1, -1, -1, -1 , -1, -1, -1)
    }

    private fun writeQuestions() {
        listQuestionView.forEachIndexed { index, questionView ->
            updateTextView(questionView.textQuestion, listQuestionText[index + 5 * page])
        }
    }

    private fun saveAnswers() {
        listQuestionView.forEachIndexed { index, modSusScaleBinding ->
            val selectedID = modSusScaleBinding.radioGroup.checkedRadioButtonId
            if (selectedID >= 0) {
                val selectedRadioButton: RadioButton = findViewById(selectedID)
                listAnswers[index + 5 * page] = selectedRadioButton.tag.toString().toInt()
                modSusScaleBinding.radioGroup.clearCheck()
            }
        }
    }

    private fun backButtonPressed() {
        saveAnswers()
        if (page == 1) {
            page = 0
            updateButtonsPage0()
        }
        setChecked()
        writeQuestions()
        logAnswers()
    }

    private fun nextButtonPressed() {
        saveAnswers()
        if (page == 0) {
            page = 1
            updateButtonsPage1()
        }
        setChecked()
        writeQuestions()
        logAnswers()
    }

    private fun updateButtonsPage1() {
        setColorButton(buttonNext, colorButtonInactive)
        setColorButton(buttonBack, colorButtonActive)
        updateTextView(textViewPageIndicator, "(" + getString(R.string.page) + " 2/2)")
    }

    private fun updateButtonsPage0() {
        setColorButton(buttonNext, colorButtonActive)
        setColorButton(buttonBack, colorButtonInactive)
        updateTextView(textViewPageIndicator, "(" + getString(R.string.page) + " 1/2)")
    }

    private fun setChecked() {
        listQuestionView.forEachIndexed{index, modSusScaleBinding ->
            val radioGroup = modSusScaleBinding.radioGroup
            val checkedValue = listAnswers[index + 5 * page]
            if (checkedValue >= 0) {
                val radioButton = radioGroup.getChildAt(checkedValue) as RadioButton
                radioButton.isChecked = true
            }
        }
    }

    //TODO: Delete later
    private fun logAnswers() {
        var string = ""
        listAnswers.forEach {
            string += "$it: "
        }
        Log.e("test", string)
    }

    private fun continueButtonPressed() {
        saveAnswers()
        logAnswers()

        if (checkAllRadioButtonsSet()) {

            dbAddSUS(participant.getID(), activeInterfaceTyp, listAnswers, this)

            if (listUsedInterfaceTypes.isNotEmpty()) {
                activeInterfaceTyp = listUsedInterfaceTypes.removeFirst()
                updateTextView(textViewInterfaceTyp, activeInterfaceTyp.value)
                listAnswers = initListAnswers()
                page = 0
                updateButtonsPage0()
                writeQuestions()
                listQuestionView.forEach {
                    it.radioGroup.clearCheck()
                }
            } else {
                Log.e("Finished", "All SuS complete")
                startNewActivity(participant, FeedBackActivity::class.java)
            }
        } else {
            setChecked()
            Toast.makeText(this, getString(R.string.not_all_fields_filled), Toast.LENGTH_LONG).show()
        }
    }

    private fun checkAllRadioButtonsSet(): Boolean {
        listAnswers.forEach {
            if (it < 0)
                return false
        }
        return true
    }

}
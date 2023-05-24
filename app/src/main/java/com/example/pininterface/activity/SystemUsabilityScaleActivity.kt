package com.example.pininterface.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.pininterface.interfaces.InterfaceGson
import com.example.pininterface.interfaces.InterfaceViewManipulation
import com.example.pininterface.R
import com.example.pininterface.database.interfaces.InterfaceDbSUS
import com.example.pininterface.databinding.ActivitySystemUsabilityScaleBinding
import com.example.pininterface.databinding.ModSusScaleBinding
import com.example.pininterface.enums.EnumInterfaceTypes
import com.example.pininterface.activity.helper.SuperActivityNavigation
import com.example.pininterface.logic.Participant

/**
 * SystemUsabilityScaleActivity (SUS)
 */
class SystemUsabilityScaleActivity : SuperActivityNavigation(), InterfaceViewManipulation, InterfaceGson, InterfaceDbSUS {

    private lateinit var participant: Participant
    private lateinit var binding: ActivitySystemUsabilityScaleBinding

    private var toast_sus: Toast? = null

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

    /**
     * onCreate
     */
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

        buttonContinue.visibility = View.INVISIBLE

        listQuestionText = mutableListOf(getString(R.string.sus_00), getString(R.string.sus_01),
            getString(R.string.sus_02), getString(R.string.sus_03), getString(R.string.sus_04),
            getString(R.string.sus_05), getString(R.string.sus_06), getString(R.string.sus_07),
            getString(R.string.sus_08), getString(R.string.sus_09))

        colorButtonInactive = ContextCompat.getColor(this, R.color.button_inactive)
        colorButtonActive = ContextCompat.getColor(this, R.color.button_active)

        val intent = intent
        participant = getParticipantFromJson(intent)

        listUsedInterfaceTypes = participant.getUsedInterfaces()
        if (listUsedInterfaceTypes.isEmpty()) { // should never happen
            Log.e("listUsedInterfaceTypes", "list is empty")
            startNewActivity(participant, FeedBackActivity::class.java)
        }
        activeInterfaceTyp = listUsedInterfaceTypes.removeFirst()

        buttonContinue.setOnClickListener { continueButtonPressed() }

        buttonNext.setOnClickListener { buttonPressedNextOrBack(buttonNext) }
        buttonBack.setOnClickListener { buttonPressedNextOrBack(buttonBack) }

        writeQuestions()
        updateTextView(textViewInterfaceTyp, this.resources.getString(activeInterfaceTyp.stringResId))
        updateButtonsNextAndBack(buttonNext, buttonBack)

        for (questionView in listQuestionView) {
            val radioGroup: RadioGroup = questionView.radioGroup
            radioGroup.setOnCheckedChangeListener { _, _ ->
                saveAnswers()
                if (checkAllRadioButtonsSet())
                    buttonContinue.visibility = View.VISIBLE
            }
        }

    }

    /**
     * Creates a MutableList with size 10 filled with -1
     * List will be used to uncheck RadioButtons
     */
    private fun initListAnswers(): MutableList<Int> {

        return mutableListOf(-1, -1, -1, -1, -1, -1, -1 , -1, -1, -1)
    }

    /**
     * Writes the SUS questions.
     * There are 10 SUS questions total, but only 5 can be displayed on each page.
     * This function can detect which questions should be displayed
     */
    private fun writeQuestions() {

        listQuestionView.forEachIndexed { index, questionView ->
            updateTextView(questionView.textQuestion, listQuestionText[index + 5 * page])
        }
    }

    /**
     * Saves the which RadioButtons are checked/saves the Answers in listAnswers
     */
    private fun saveAnswers() {

        listQuestionView.forEachIndexed { index, modSusScaleBinding ->
            val selectedID = modSusScaleBinding.radioGroup.checkedRadioButtonId
            if (selectedID >= 0) {
                val selectedRadioButton: RadioButton = findViewById(selectedID)
                listAnswers[index + 5 * page] = selectedRadioButton.tag.toString().toInt()
            }
        }
    }

    /**
     * clear/uncheck all checked RadioButtons
     */
    private fun clearAnswers() {

        listQuestionView.forEach {
            it.radioGroup.clearCheck()
        }
    }

    /**
     * Saves answers in listAnswers
     * If on page 1 and back button pressed changes to page 0
     * If on page 0 and next button pressed changes to page 1
     * Updates next and back Button color
     * Sets checked RadioButtons
     * Writes the sus Questions
     * @param pButton next or back button
     */
    private fun buttonPressedNextOrBack(pButton: Button) {

        clearAnswers()

        if (page == 1 && pButton == buttonBack) {
            page = 0
            updateButtonsNextAndBack(buttonNext, buttonBack)
        } else if (page == 0 && pButton == buttonNext) {
            page = 1
            updateButtonsNextAndBack(buttonBack, buttonNext)
        }

        setChecked()
        writeQuestions()
    }

    /**
     * Updates the Back and Next Button/sets their color and updates the pageIndicator textView
     * @param pButtonActivate active Button
     * @param pButtonInactive inactive Button
     */
    private fun updateButtonsNextAndBack(pButtonActivate: Button, pButtonInactive: Button) {

        setColorButton(pButtonActivate, colorButtonActive)
        setColorButton(pButtonInactive, colorButtonInactive)
        val pageIndicator = if (pButtonActivate == buttonNext)
            "(" + getString(R.string.page) + " 1/2)"
        else
            "(" + getString(R.string.page) + " 2/2)"

        updateTextView(textViewPageIndicator, pageIndicator)
    }

    /**
     * Sets the checked RadioButtons
     */
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

    /**
     * buttonContinuePressed
     * saves answers to listAnswers
     * checks if answers are complete
     * Toast with error message if answers are not complete
     * If answers complete commits answers to DB
     * Then checks if there is another interface available
     * If no other interface it will start the next activity (feedback)
     * if there is another interface it will reset sus view
     */
    private fun continueButtonPressed() {

        clearAnswers()
        buttonContinue.visibility = View.INVISIBLE

        if (checkAllRadioButtonsSet()) { // kann man entfernen da button nur sichtbar ist wenn alle gesetzt

            dbAddSUS(participant.getID(), activeInterfaceTyp, listAnswers, this)

            if (listUsedInterfaceTypes.isNotEmpty()) {
                activeInterfaceTyp = listUsedInterfaceTypes.removeFirst()
                updateTextView(textViewInterfaceTyp, this.resources.getString(activeInterfaceTyp.stringResId))
                listAnswers = initListAnswers()
                page = 0
                updateButtonsNextAndBack(buttonNext, buttonBack)
                writeQuestions()
                listQuestionView.forEach {
                    it.radioGroup.clearCheck()
                }
            } else {
                toast_sus?.cancel()
                startNewActivity(participant, FeedBackActivity::class.java)
            }
        } else {
            setChecked()
            toast_sus = Toast.makeText(this, getString(R.string.not_all_fields_filled), Toast.LENGTH_SHORT)
            toast_sus?.show()
        }
    }

    /**
     * checks if all the RadioButtons are set
     * @return true if all RadioButtons are set, false if not
     */
    private fun checkAllRadioButtonsSet(): Boolean {

        listAnswers.forEach {
            if (it < 0)
                return false
        }
        return true
    }

}
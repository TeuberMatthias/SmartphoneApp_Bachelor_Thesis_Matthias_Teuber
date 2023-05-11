package com.example.pininterface.activity.helper

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.pininterface.R
import com.example.pininterface.interfaces.InterfaceGson
import com.example.pininterface.interfaces.InterfaceViewManipulation
import com.example.pininterface.activity.IntermediatePageActivity
import com.example.pininterface.database.interfaces.InterfaceDbInputSubmission
import com.example.pininterface.enums.EnumButtonTypes
import com.example.pininterface.enums.EnumInterfaceTypes
import com.example.pininterface.logic.Participant
import com.example.pininterface.logic.TimeDifferenceCalculator
import java.util.*

/**
 * Super Class for the Interface Layouts
 */
open class SuperLayoutActivity : SuperActivityNavigation(), InterfaceViewManipulation, InterfaceDbInputSubmission, InterfaceGson {

    open lateinit var interfaceType: EnumInterfaceTypes
    lateinit var participant: Participant

    lateinit var pinSubmissionTextView: TextView
    lateinit var pinTargetTextView: TextView

    private var timeDif = TimeDifferenceCalculator()

    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button
    lateinit var button4: Button
    lateinit var button5: Button
    lateinit var button6: Button
    lateinit var button7: Button
    lateinit var button8: Button
    lateinit var button9: Button
    lateinit var button0: Button
    var listNumButtons = mutableListOf<Button>()

    lateinit var buttonDel: Button
    lateinit var buttonAccept: Button
    var listCtrlButtons = mutableListOf<Button>()

    lateinit var buttonEmergency: Button

    private var timer: Timer? = null
    private var winActive = true

    var colorControlNormal: Int = 0
    var colorControlDeactivated: Int = 0
    var colorControlHighlighted: Int = 0

    var colorNumNormal: Int = 0
    var colorNumDeactivated: Int = 0
    var colorNumHighlighted: Int = 0


    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //gets the current participant object from the intent via gson
        val intent = intent
        participant = getParticipantFromJson(intent)

        colorControlNormal = ContextCompat.getColor(this, R.color.control_button_normal)
        colorControlDeactivated = ContextCompat.getColor(this, R.color.control_button_deactivated)
        colorControlHighlighted = ContextCompat.getColor(this, R.color.control_button_highlighted)

        colorNumNormal = ContextCompat.getColor(this, R.color.number_button_normal)
        colorNumDeactivated = ContextCompat.getColor(this, R.color.number_button_deactivated)
        colorNumHighlighted = ContextCompat.getColor(this, R.color.number_button_highlighted)
    }

    /**
     * Adds a Submission to the DB with the time since the last submission
     * @param pSubmission Submission as EnumButtonTypes
     */
    private fun addSubmission(pSubmission: EnumButtonTypes) {

        val time = timeDif.calcTimeDif()
        dbAddSubmission(participant.getID(), participant.getActiveInterface(), participant.getActivePin().getPin(), pSubmission, time, this)
    }

    /**
     * Emergency Button clicked
     * Adds Emergency Button as Submission to DB
     */
    private fun emergencyButtonClicked() {

        addSubmission(EnumButtonTypes.EMERGENCY)
    }

    /**
     * Delete Button clicked
     * Adds Delete Button as Submission to DB
     * Cancels active timer for User Pin submission textView
     * Updates User Pin submission textView
     */
    private fun delButtonClicked() {

        timer?.cancel()
        updateTextView(pinSubmissionTextView, hidePinString(participant.deleteLastDigit()))
        addSubmission(EnumButtonTypes.DEL)
    }

    /**
     * Accept Button clicked
     * Adds Accept Button as Submission to DB
     * Cancels active timer for User Pin submission textView
     * Checks if pin is solved
     * If Pin solved checks if current interface is still active
     * If current interface is inactive go to next Activity (IntermediatePageActivity)
     * If current Interface still get next Pin and update Pin submission and target TextView
     * If pin is incorrect display wrong pin message in pin submission TextView
     */
    private fun acceptButtonClicked() {

        addSubmission(EnumButtonTypes.SUBMIT)
        val cInterfaceType = interfaceType
        timer?.cancel()

        if (participant.checkActivePinSolved()) {
            if (participant.getActiveInterface() == cInterfaceType) {
                updateTextView(pinSubmissionTextView, participant.getActivePin().getPinSubmission())
                updateTextView(pinTargetTextView, participant.getActivePin().getPin())
            } else {
                startNewActivity(participant, IntermediatePageActivity::class.java)
            }
        } else {
            updateTextView(pinSubmissionTextView, getString(R.string.wrong_pin))
            startTimerPinSubmissionTextViewUpdate(hidePinString(participant.getActivePin().getPinSubmission()))
        }
    }

    /**
     * Number Button clicked
     * Adds the clicked Number Button as Submission to DB
     * Updates pin submission TextView
     * @param pButton Number Button
     */
    private fun numButtonClicked(pButton: EnumButtonTypes) {

        addSubmission(pButton)

        val pinSubmissionOld = participant.getActivePin().getPinSubmission()
        val pinSubmissionNew = participant.numButtonClicked(pButton)

        updateTextView(pinSubmissionTextView, hidePinString(pinSubmissionOld) + pButton.value)
        startTimerPinSubmissionTextViewUpdate(hidePinString(pinSubmissionNew))

        // used when the length of pin submission was restricted to the length of target pin
        /*
        if (pinSubmissionNew != pinSubmissionOld) {
            updateTextView(pinSubmissionTextView, hidePinString(pinSubmissionOld) + pButton.value)
            startTimerPinSubmissionTextViewUpdate(hidePinString(pinSubmissionNew))
        } else
            updateTextView(pinSubmissionTextView, hidePinString(pinSubmissionOld))
        */
    }

    /**
     * Updates Pin Submission TextView after a delay (1000ms)
     * Cancels older timers
     * @param pUpdate new pin submission TextView text
     */
    private fun startTimerPinSubmissionTextViewUpdate(pUpdate: String) {

        timer?.cancel()

        val myTimerTask = object : TimerTask() {
            override fun run() {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    updateTextView(pinSubmissionTextView, pUpdate)
                }
                timer?.cancel()
            }
        }

        timer = Timer()
        timer?.schedule(myTimerTask, 1000)
    }

    /**
     * Back Button clicked
     * Adds back button submission to DB
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        addSubmission(EnumButtonTypes.BACK)
    }

    /**
     * App on Pause / home Button pressed (or recent apps and then clicked away)
     * Adds WinDeactivated as submission to DB
     */
    override fun onPause() {

        super.onPause()
        winActive = false
        addSubmission(EnumButtonTypes.WIN_DEACTIVE)
    }

    /**
     * onResume
     * App is back from being inactive
     * Adds WinActivated as submission to DB
     */
    override fun onResume() {

        super.onResume()
        if (!winActive) {
            winActive = true
            addSubmission(EnumButtonTypes.WIN_ACTIVE)
        }
    }

    /*
    //TODO("Doesn't detect "recent apps" button when not clicked out")
    override fun onUserLeaveHint() {
        Log.e("test", "userLeaveHit")
        super.onUserLeaveHint()
    }
    */

    /**
     * Sets interfaceType
     * @param pInterfaceType interfaceType
     */
    fun setInterfaceTypeForLayout(pInterfaceType: EnumInterfaceTypes) {

        interfaceType = pInterfaceType
    }

    /**
     * Initiates button Listeners for Number, Delete, Accept and Emergency Button
     */
    open fun buttonListeners() {
        listNumButtons.forEachIndexed { index, button ->
            button.setOnClickListener { numButtonClicked(EnumButtonTypes.values()[index]) }
        }
        buttonDel.setOnClickListener { delButtonClicked() }
        buttonAccept.setOnClickListener { acceptButtonClicked() }
        buttonEmergency.setOnClickListener { emergencyButtonClicked() }
    }
}


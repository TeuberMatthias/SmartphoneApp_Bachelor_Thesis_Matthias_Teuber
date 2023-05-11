package com.example.pininterface.activity.helper

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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


open class SuperLayoutActivity : SuperActivityNavigation(), InterfaceViewManipulation, InterfaceDbInputSubmission,
    InterfaceGson {
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //gets the current participant object from the intent via gson
        val intent = intent
        participant = getParticipantFromJson(intent)
        //participant.resetSubmissionPinTimer()

        colorControlNormal = ContextCompat.getColor(this, R.color.control_button_normal)
        colorControlDeactivated = ContextCompat.getColor(this, R.color.control_button_deactivated)
        colorControlHighlighted = ContextCompat.getColor(this, R.color.control_button_highlighted)

        colorNumNormal = ContextCompat.getColor(this, R.color.number_button_normal)
        colorNumDeactivated = ContextCompat.getColor(this, R.color.number_button_deactivated)
        colorNumHighlighted = ContextCompat.getColor(this, R.color.number_button_highlighted)
    }

    private fun addSubmission(pSubmission: EnumButtonTypes) {

        //TODO: get Time
        val time = timeDif.calcTimeDif()
        dbAddSubmission(participant.getID(), participant.getActiveInterface(), participant.getActivePin().getPin(), pSubmission, time, this)
    }

    private fun emergencyButtonClicked() {

        addSubmission(EnumButtonTypes.EMERGENCY)
    }

    private fun delButtonClicked() {
        timer?.cancel()
        updateTextView(pinSubmissionTextView, hidePinString(participant.deleteLastDigit()))
        addSubmission(EnumButtonTypes.DEL)
    }

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

    private fun numButtonClicked(pButton: EnumButtonTypes) {
        addSubmission(pButton)

        val pinSubmissionOld = participant.getActivePin().getPinSubmission()
        val pinSubmissionNew = participant.numButtonClicked(pButton)

        if (pinSubmissionNew != pinSubmissionOld) {
            updateTextView(pinSubmissionTextView, hidePinString(pinSubmissionOld) + pButton.value)
            startTimerPinSubmissionTextViewUpdate(hidePinString(pinSubmissionNew))
        } else
            updateTextView(pinSubmissionTextView, hidePinString(pinSubmissionOld))
    }

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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        addSubmission(EnumButtonTypes.BACK)
        //participant.addSubmission(EnumButtonTypes.BACK)
    }

    override fun onPause() {
        super.onPause()
        Log.e("test", "onPause")
        winActive = false
        addSubmission(EnumButtonTypes.WIN_DEACTIVE)
    }

    override fun onResume() {
        super.onResume()
        Log.e("test", "onResume")
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

    fun setInterfaceTypeForLayout(pInterfaceType: EnumInterfaceTypes) {
        interfaceType = pInterfaceType
    }

    open fun buttonListeners() {
        listNumButtons.forEachIndexed { index, button ->
            button.setOnClickListener { numButtonClicked(EnumButtonTypes.values()[index]) }
        }
        buttonDel.setOnClickListener { delButtonClicked() }
        buttonAccept.setOnClickListener { acceptButtonClicked() }
        buttonEmergency.setOnClickListener { emergencyButtonClicked() }
    }
}


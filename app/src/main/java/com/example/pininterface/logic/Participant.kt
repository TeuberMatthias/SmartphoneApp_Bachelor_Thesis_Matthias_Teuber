package com.example.pininterface.logic

import com.example.pininterface.enums.EnumButtonTypes
import com.example.pininterface.enums.EnumInterfaceTypes

/**
 * Participant
 * @param pId Participant ID
 * @param pParticipantPinSets the pinSets for this participant
 */
class Participant (pId: Int, pParticipantPinSets: PinSets){
    private val id: Int = pId
    // The Interfaces that will be used for this participant. Order randomized
    private val listInterfaces = ArrayList(arrayListOf(EnumInterfaceTypes.STANDARD, EnumInterfaceTypes.STANDARD_VIS, EnumInterfaceTypes.COLUMN, EnumInterfaceTypes.COLUMN_VIS).shuffled())
    private lateinit var activeInterface: EnumInterfaceTypes
    private var listUsedInterfaces = mutableListOf<EnumInterfaceTypes>()

    private var listParticipantPinSets = pParticipantPinSets.getPinSets()
    private var listActivePinSet = ArrayList<Pin>()
    private lateinit var activePin: Pin

    private var listSubmissionsPin = ArrayList<String>()
    private lateinit var buttonInputPin: ButtonInputs

    private lateinit var demographics: Demographics
    private var listSuSResults = mutableListOf<SuSResults>()
    private var feedback = ""

    init {
        nextActiveInterface()
        setActivePin()
        setActiveSubmissionsPin(activeInterface, activePin.getPin())
    }

    fun getID(): Int {
        return this.id;
    }

    /**
     * Checks if there is a next interface in interface ArrayList and pinSet in participantPinSets ArrayList.
     * If true, it will delete the first interface and pinSet
     * @return "true" if there is a next interface and PinSet. "false" if there isn't another interface OR pinSet
     */
    private fun nextActiveInterface(): Boolean {
        if (listInterfaces.isNotEmpty()) {
            if (listParticipantPinSets.isNotEmpty()) {
                this.activeInterface = listInterfaces.removeFirst()
                this.listUsedInterfaces.add(activeInterface)
                this.listActivePinSet = listParticipantPinSets.removeFirst()
                return true
            }
        }
        return false
    }

    fun setFeedBack (pFeedBack: String) {
        this.feedback = pFeedBack
    }

    fun getFeedBack(): String {
        return this.feedback
    }

    fun getUsedInterfaces(): MutableList<EnumInterfaceTypes> {
        return this.listUsedInterfaces.toMutableList()
    }

    fun addSuSResult(pSuSResults: SuSResults) {
        this.listSuSResults.add(pSuSResults)
    }

    /**
     * setActivePin
     * if activePinSet is not empty, it will remove the first pin and set this as activePin
     * @return "true" if activePinSet is not empty. "false" if activePinSet is empty
     */
    private fun setActivePin(): Boolean {
        if (listActivePinSet.isNotEmpty()) {
            this.activePin = listActivePinSet.removeFirst()
            return true
        }
        return false
    }

    /**
     *
     */
    private fun setActiveSubmissionsPin(pActiveInterfaceType: EnumInterfaceTypes, pPin: String) {
        this.buttonInputPin = ButtonInputs(pActiveInterfaceType, pPin)
    }

    /**
     * Adds a new Button Input to Submission
     */
    fun addSubmission(pButton: EnumButtonTypes) {
        buttonInputPin.addSubmissionEntry(pButton)
    }

    /**
     *
     */
    fun numButtonClicked(pButton: EnumButtonTypes): String {
        return activePin.addDigit(pButton.value)
    }

    /**
     * Checks if the active Pin is solved
     * When true, the current
     */
    fun checkActivePinSolved(): Boolean {
        val solved = activePin.checkSolved()

        if (solved) {
            this.listSubmissionsPin.add(buttonInputPin.submissionListToString())
            if (setActivePin()) {
                setActiveSubmissionsPin(activeInterface, activePin.getPin())
            } else if (nextActiveInterface()) {
                setActivePin()
                setActiveSubmissionsPin(activeInterface, activePin.getPin())
            } else {
                activeInterface = EnumInterfaceTypes.NONE
            }
        }
        return solved
    }

    /**
     * Deletes Last Digit of the active Pin
     * @return The new Pin Submission Field String
     */
    fun deleteLastDigit(): String {
        return activePin.deleteLastDigit()
    }

    fun getActivePin(): Pin {
        return activePin
    }


    /**
     * Gets the currently active Interface Type
     * @return The currently active Interface Type as Enum
     */
    fun getActiveInterface(): EnumInterfaceTypes {
        return this.activeInterface
    }

    /**
     * Gets all the submissions as Strings in Array List
     * @return submissions as ArrayList<String>
     */
    fun getSubmissionPinList(): ArrayList<String> {
        return this.listSubmissionsPin
    }

    fun resetSubmissionPinTimer() {
        buttonInputPin.resetTimer()
    }

    fun setDemographics(pDemographics: Demographics) {
        demographics = pDemographics
    }

    fun getDemographics(): Demographics {
        return demographics
    }

}
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
    private val listInterfaces = ArrayList(arrayListOf(EnumInterfaceTypes.STANDARD/*, EnumInterfaceTypes.STANDARD_VIS, EnumInterfaceTypes.COLUMN, EnumInterfaceTypes.COLUMN_VIS*/).shuffled())
    private lateinit var activeInterface: EnumInterfaceTypes
    private var listUsedInterfaces = mutableListOf<EnumInterfaceTypes>()

    private var listParticipantPinSets = pParticipantPinSets.getPinSets()
    private var listActivePinSet = ArrayList<Pin>()
    private lateinit var activePin: Pin

    private val listInterfacesAsString = listInterfacesAsString()
    private val listPinSetsAsString = listPinSetsAsString()

   // private lateinit var buttonInputPin: ButtonInputs

    init {
        nextActiveInterface()
        setActivePin()
        //setActiveSubmissionsPin(activeInterface, activePin.getPin())
    }

    fun getID(): Int {
        return this.id
    }

    fun getPinSetsAsString(): String {
        return listPinSetsAsString
    }

    fun getInterfacesAsString(): String {
        return listInterfacesAsString
    }

    private fun listInterfacesAsString(): String {
        var string = ""
        listInterfaces.forEach {
            string += it.value + ";"
        }
        return string
    }

    private fun listPinSetsAsString(): String {
        var string = ""

        listParticipantPinSets.forEach { pinSet ->
            string += "("
            pinSet.forEach { pin ->
                string += pin.getPin() + ";"
            }
            string = string.substring(0, string.length - 1) + ")"
        }
        return string
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

    fun getUsedInterfaces(): MutableList<EnumInterfaceTypes> {
        return this.listUsedInterfaces.toMutableList()
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
    fun numButtonClicked(pButton: EnumButtonTypes): String {
        return activePin.addDigit(pButton.value)
    }

    fun checkActivePinSolved(): Boolean {
        val solved = activePin.checkSolved()

        if (solved) {
            if (setActivePin()) {
                return true
            } else if (nextActiveInterface()) {
                setActivePin()
            } else {
                activeInterface = EnumInterfaceTypes.NONE
            }
        }
        return solved
    }

    /**
     * Deletes the last digit of the currently active Pin
     * @return The new Pin Submission Field String
     */
    fun deleteLastDigit(): String {
        return activePin.deleteLastDigit()
    }

    /**
     * Gets the currently active pin
     * @return The currently active pin as Pin
     */
    fun getActivePin(): Pin {
        return activePin
    }

    /**
     * Gets the currently active Interface Type
     * @return The currently active Interface Type as EnumInterfaceTypes
     */
    fun getActiveInterface(): EnumInterfaceTypes {
        return this.activeInterface
    }
}
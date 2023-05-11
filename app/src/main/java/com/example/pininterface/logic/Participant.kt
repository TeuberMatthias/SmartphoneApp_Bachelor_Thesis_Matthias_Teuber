package com.example.pininterface.logic

import com.example.pininterface.enums.EnumButtonTypes
import com.example.pininterface.enums.EnumInterfaceTypes
import com.example.pininterface.values.PinSets

/**
 * Participant
 * @param pId Participant ID
 * @param pParticipantPinSets the pinSets for this participant
 */
class Participant (pId: Int, pParticipantPinSets: PinSets){

    // The unique id for the participant
    private val id: Int = pId
    // The Interfaces that will be used for this participant. Order randomized. Once an interface becomes active, it will be removed from this list
    private val listInterfaces = ArrayList(arrayListOf(EnumInterfaceTypes.STANDARD/*, EnumInterfaceTypes.STANDARD_VIS, EnumInterfaceTypes.COLUMN, EnumInterfaceTypes.COLUMN_VIS*/).shuffled())
    // The currently active Interface
    private lateinit var activeInterface: EnumInterfaceTypes
    // List of the already used Interface, in the same order as they were used
    private var listUsedInterfaces = mutableListOf<EnumInterfaceTypes>()

    // List of the participants PinSets. Order randomized (in PinSets.kt). Once a PinSet becomes active, it will be removed from this list
    private var listParticipantPinSets = pParticipantPinSets.getPinSets()
    // The currently active PinSet, a list of Pin. Once a Pin becomes active, it will be removed from this list
    private var listActivePinSet = mutableListOf<Pin>()
    // The currently active Pin
    private lateinit var activePin: Pin

    // Lists of the Interfaces and PinSets with the participant specific order
    // Only used for the DB participant table
    private val listInterfacesAsString = listInterfacesAsString()
    private val listPinSetsAsString = listPinSetsAsString()

    // activates the first Interface and pin, removes both from their list
    init {

        nextActiveInterface()
        setActivePin()
    }

    /**
     * Gets the unique participant id
     * @return the unique participant id as Int
     */
    fun getID(): Int {

        return this.id
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
     * Adds a digit to the user submission pin
     * @return the new user submission pin as String
     */
    fun numButtonClicked(pButton: EnumButtonTypes): String {

        return activePin.addDigit(pButton.value)
    }

    /**
     * Checks if the currently active pin is solved
     * If true, it will try to get the next pin from the currently active PinSet
     * and set this as the new active pin.
     * If there the currently active PinSet is empty, it will try to active the next Interface
     * if there is no next interface it will set active interface to "NONE"
     * @return true if Pin is solved, false if not
     */
    fun checkActivePinSolved(): Boolean {

        val solved = activePin.checkSolved()

        if (solved) {
            if (setActivePin())
                return true // the next pin form the current PinSet is set as active pin
            else if (nextActiveInterface()) {
                if (!setActivePin())
                    activeInterface = EnumInterfaceTypes.NONE // next Interface doesn't has Pin in PinSet (shouldn't happen)
            } else
                activeInterface = EnumInterfaceTypes.NONE // no more interfaces
        }
        return solved
    }

    /**
     * Deletes the last digit of the User Pin Submission
     * @return The new User Pin Submission as String
     */
    fun deleteLastDigit(): String {

        return activePin.deleteLastDigit()
    }

    /**
     * Gets the currently active pin
     * @return The currently active pin as Pin
     */
    fun getActivePin(): Pin {

        return this.activePin
    }

    /**
     * Gets the currently active Interface Type
     * @return The currently active Interface Type as EnumInterfaceTypes
     */
    fun getActiveInterface(): EnumInterfaceTypes {

        return this.activeInterface
    }

    /**
     * Gets the list of Used Interfaces in order of their usage, used in SUS
     * @return list of Used Interfaces as MutableList<EnumInterfaceTypes>
     */
    fun getUsedInterfaces(): MutableList<EnumInterfaceTypes> {

        return this.listUsedInterfaces.toMutableList()
    }

    /**
     * Gets the PinSets as String, used for DB
     * @return the PinSets as String
     */
    fun getPinSetsAsString(): String {

        return this.listPinSetsAsString
    }

    /**
     * Gets the Interfaces as String, used for DB
     * @return the Interfaces as String
     */
    fun getInterfacesAsString(): String {

        return this.listInterfacesAsString
    }

    /**
     * Writes the listInterfaces as String, used for DB
     * @return the listInterfaces as String, separator: ";"
     */
    private fun listInterfacesAsString(): String {
        var string = ""
        listInterfaces.forEach {
            string += it.value + ";"
        }
        return string
    }


    /**
     * Writes the listPinSets as String, used for DB
     * @return the listPinSets as String, separator for PinSets: "|", Pins: ";"
     */
    private fun listPinSetsAsString(): String {

        var string = ""

        listParticipantPinSets.forEach { pinSet ->

            pinSet.forEach { pin ->

                string += pin.getPin() + ";"
            }
            string = string.substring(0, string.length - 1) + "|"
        }
        string = string.substring(0, string.length - 1)

        return string
    }
}
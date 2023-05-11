package com.example.pininterface.logic

/**
 * Class PIN
 * @param pPin ArrayList of the digits of the Pin
 */
class Pin (pPin: ArrayList<Int>){
    private var pin = ""
    private var pinSubmission = ""

    init {
        //transform the pin ArrayList into a pin String
        pPin.forEach {
            pin += it.toString()
        }
    }

    /**
     * Get the Pin as String
     * @return pin as String
     */
    fun getPin(): String {
        return this.pin
    }

    /**
     * Get the Pin Submission as String
     * @return PinSubmission as String
     */
    fun getPinSubmission(): String {
        return this.pinSubmission
    }

    /**
     * Checks if the Pin is solved.
     * Compares pin with pinSubmission
     * @return pin == pinSubmission
     */
    fun checkSolved(): Boolean {
        return pin == pinSubmission
    }

    /**
     * Adds a digit to the PinSubmissionString.
     * @param pChar: The digit as character that is added to the PinSubmissionString
     * @return: The PinSubmission
     */
    fun addDigit(pChar: Char): String {
        if (pinSubmission.length < pin.length)
            pinSubmission += pChar
        return pinSubmission
    }

    /**
     * deletes the last digit from the PinSubmissionString and replaces it with "_"
     * @return PinSubmissionString
     */
    fun deleteLastDigit(): String {
        pinSubmission = pinSubmission.reversed().replaceFirst("\\d".toRegex(), "").reversed()
        return pinSubmission
    }
}
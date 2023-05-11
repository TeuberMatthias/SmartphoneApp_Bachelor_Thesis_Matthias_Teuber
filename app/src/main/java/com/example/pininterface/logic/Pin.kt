package com.example.pininterface.logic

/**
 * Class PIN
 * @param pPin List of the digits as Int of the Pin
 */
class Pin (pPin: MutableList<Int>) {

    //The Pin as String
    private var pin = ""
    //The user submission for the Pin as String
    private var pinSubmission = ""

    //transform the pin Int-List into a pin String
    init {

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
     * @return user pin submission as String
     */
    fun getPinSubmission(): String {

        return this.pinSubmission
    }

    /**
     * Checks if the Pin is solved.
     * Compares pin with pinSubmission
     * @return true if user pin submission is equal to pin
     */
    fun checkSolved(): Boolean {

        return pin == pinSubmission
    }

    /**
     * Adds a digit to the PinSubmissionString.
     * @param pChar The digit as character that is added to the PinSubmissionString
     * @return the new user pin submission as String
     */
    fun addDigit(pChar: Char): String {

        /*
        would prevent the user to submit more digits then the target pin:

        if (pinSubmission.length < pin.length)
            pinSubmission += pChar
        */
        pinSubmission += pChar
        return pinSubmission
    }

    /**
     * Deletes the last digit from the pinSubmission-String
     * @return the new user pin submission as String
     */
    fun deleteLastDigit(): String {

        pinSubmission = pinSubmission.reversed().replaceFirst("\\d".toRegex(), "").reversed()
        return pinSubmission
    }
}
package com.example.pininterface.logic

import com.example.pininterface.enums.EnumButtonTypes
import com.example.pininterface.enums.EnumInterfaceTypes

class ButtonInputs (pInterfaceType: EnumInterfaceTypes, pPin: String) {
    private val interfaceType = pInterfaceType
    private val pin = pPin
    private var submissionList = ArrayList<SubmissionEntry>()

    private var oldTime = System.currentTimeMillis()

    /**
     * Adds a new submissionEntry(pButtonType, time) to submissionList.
     * @param pButtonTypes: the Button type that should be added to the submission list
     * time: currentTime - oldTime
     */
    fun addSubmissionEntry(pButtonTypes: EnumButtonTypes) {
        val newTime = System.currentTimeMillis()
        submissionList.add(SubmissionEntry(pButtonTypes, newTime-oldTime))
        oldTime = System.currentTimeMillis()
    }

    /**
     * Creates a String representation of the ButtonInput Class
     * @return: String
     */
    fun submissionListToString(): String {
        var time = 0
        var submissionString = ""
        submissionList.forEach {
            time += it.getTime().toInt()
            submissionString += it.entryToString()
        }
        return "[typ:$interfaceType;pin:$pin; t:$time; $submissionString]\n"
    }

    /**
     * resets the timer for oldTime
     */
    fun resetTimer() {
        oldTime = System.currentTimeMillis()
    }

    /**
     * Class SubmissionEntry
     * A Entry for ButtonInputs.submissionList
     * @param pButtonTypes: The Button that was activated for this Entry
     * @param pTime: The time it took since the last Entry
     */
    class SubmissionEntry(pButtonTypes: EnumButtonTypes, pTime: Long) {
        private val buttonType = pButtonTypes
        private val time = pTime

        /**
         * Getter time
         * @return: time
         */
        fun getTime(): Long {
            return time
        }

        /**
         * Creates a String of the Object SubmissionEntry
         * @return String of the SubmissionEntry
         */
        fun entryToString(): String {
            return "($buttonType,$time)"
        }
    }

}
package com.example.pininterface.logic

/**
 * TimeDifferenceCalculator
 * Class for calculating the TimeDifference
 */
class TimeDifferenceCalculator {

    private var oldTime = System.currentTimeMillis()

    /**
     * reset the old time to the current time
     */
    fun resetTime() {

        this.oldTime = System.currentTimeMillis()
    }

    /**
     * Calcuate the time difference between the old time and the current time.
     * Once finished it will set the old time as the current time.
     * @return the time difference as Int
     */
    fun calcTimeDif(): Int {

        val currentTime = System.currentTimeMillis()
        val timeDifference = currentTime - this.oldTime
        this.oldTime = currentTime
        return timeDifference.toInt()
    }
}
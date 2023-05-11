package com.example.pininterface.logic

class TimeDifferenceCalculator {
    private var oldTime = System.currentTimeMillis()

    fun resetTime() {
        this.oldTime = System.currentTimeMillis()
    }

    fun calcTimeDif(): Int {
        val newTime = System.currentTimeMillis()
        val timeDifference = newTime - this.oldTime
        this.oldTime = newTime
        return timeDifference.toInt()
    }
}
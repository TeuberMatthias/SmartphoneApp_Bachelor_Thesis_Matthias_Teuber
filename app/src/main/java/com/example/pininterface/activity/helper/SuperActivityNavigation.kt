package com.example.pininterface.activity.helper

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pininterface.database.interfaces.Vibrate
import com.example.pininterface.logic.Participant
import com.google.gson.Gson

/**
 * Super Class with a function to go to start a new Activity
 */
open class SuperActivityNavigation : AppCompatActivity(), Vibrate {

    /**
     * Starts a new Activity
     * clears all previous Activities
     * @param pParticipant participant
     * @param pLayoutClass new Activity
     */
    inline fun <reified T> startNewActivity(pParticipant: Participant, pLayoutClass: Class<T>) {

        vibrate(this, 50)

        val gson = Gson()
        val intent = Intent(this, pLayoutClass).apply {
            putExtra("participant", gson.toJson(pParticipant))
        }

        // clears previous Activities
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        startActivity(intent)
    }

    /**
     * Overrides the Back Button
     * Back Button does nothing except trigger a Toast
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        Toast.makeText(this, "Back Button disabled", Toast.LENGTH_SHORT).show()
    }
/*
    /**
     * vibrate for a set amount of time
     * @param pTime time how long the phone should vibrate (Long)
     */
    @RequiresApi(Build.VERSION_CODES.S)
    fun vibrate(pTime: Long) {

        if (vibrator.defaultVibrator.hasVibrator()) {
            vibrator.defaultVibrator.vibrate(VibrationEffect.createOneShot(pTime, VibrationEffect.DEFAULT_AMPLITUDE))
        }
    }

    /**
     * Stops the Vibrator if activity is destroyed
     */
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onDestroy() {

        super.onDestroy()
        vibrator.cancel()
    }

 */
}
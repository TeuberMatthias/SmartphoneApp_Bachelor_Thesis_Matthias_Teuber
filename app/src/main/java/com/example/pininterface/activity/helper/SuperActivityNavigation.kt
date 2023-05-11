package com.example.pininterface.activity.helper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import com.example.pininterface.logic.Participant
import com.google.gson.Gson

open class SuperActivityNavigation : AppCompatActivity() {
    
    /**
     * Starts a new Activity
     * @param pParticipant participant
     * @param pLayoutClass new Activity
     */
    inline fun <reified T> startNewActivity(pParticipant: Participant, pLayoutClass: Class<T>) {

        val gson = Gson()
        val intent = Intent(this, pLayoutClass).apply {
            putExtra("participant", gson.toJson(pParticipant))
        }
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK // clears previous Activities
        startActivity(intent)
    }
}
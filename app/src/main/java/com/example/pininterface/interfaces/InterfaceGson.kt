package com.example.pininterface.interfaces

import android.content.Intent
import com.example.pininterface.logic.Participant
import com.google.gson.Gson

/**
 * Interface for Gson
 */
interface InterfaceGson {

    /**
     * Gets an Participant object from an intent
     * @param pIntent intent as Intent
     * @return participant as Participant
     */
    fun getParticipantFromJson(pIntent: Intent): Participant {
        val gson = Gson()
        return gson.fromJson<Participant>(
            pIntent.getStringExtra("participant"),
            Participant::class.java
        )
    }
}
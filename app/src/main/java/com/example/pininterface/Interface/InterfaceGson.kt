package com.example.pininterface.Interface

import android.content.Intent
import com.example.pininterface.logic.Participant
import com.google.gson.Gson

interface InterfaceGson {

    fun getParticipantFromJson(intent: Intent): Participant {
        val gson = Gson()
        return gson.fromJson<Participant>(
            intent.getStringExtra("participant"),
            Participant::class.java
        )
    }
}
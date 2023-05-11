package com.example.pininterface.activity.helper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.pininterface.activity.DemographicActivity
import com.example.pininterface.activity.layout.LayoutColumnActivity
import com.example.pininterface.activity.layout.LayoutColumnVisualAidActivity
import com.example.pininterface.activity.layout.LayoutStandardActivity
import com.example.pininterface.activity.layout.LayoutStandardVisualAidActivity
import com.example.pininterface.logic.Participant
import com.example.pininterface.enums.EnumInterfaceTypes
import com.google.gson.Gson

open class SuperActivityNavigation : AppCompatActivity() {

    /**
     * Goes to the Next InterfaceActiviy, depending of the active Interface of the current Participant.
     * @param pParticipant: The current participant
     */
    fun nextInterfaceActivity(pParticipant: Participant) {
        when (pParticipant.getActiveInterface()) {
            EnumInterfaceTypes.STANDARD     -> startNewActivity(pParticipant, LayoutStandardActivity::class.java)
            EnumInterfaceTypes.COLUMN       -> startNewActivity(pParticipant, LayoutColumnActivity::class.java)
            EnumInterfaceTypes.STANDARD_VIS -> startNewActivity(pParticipant, LayoutStandardVisualAidActivity::class.java)
            EnumInterfaceTypes.COLUMN_VIS   -> startNewActivity(pParticipant, LayoutColumnVisualAidActivity::class.java)
            else                        -> startNewActivity(pParticipant, DemographicActivity::class.java) // no active Interface/Active Interface = NONE
        }
    }

    /**
     * Starts a new Activity
     * @param pParticipant: participant
     * @param pLayoutClass: The next Activity
     */
    inline fun <reified T> startNewActivity(pParticipant: Participant, pLayoutClass: Class<T>) {
        val gson = Gson()
        val intent = Intent(this, pLayoutClass).apply {
            putExtra("participant", gson.toJson(pParticipant))
        }
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK // clears all previous Activities
        startActivity(intent)
    }
}
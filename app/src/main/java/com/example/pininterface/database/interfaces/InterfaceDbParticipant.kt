package com.example.pininterface.database.interfaces

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.modelclass.ModelClassParticipant
import com.example.pininterface.logic.Participant

interface InterfaceDbParticipant {

    fun dbViewListParticipant(pContext: Context): MutableList<ModelClassParticipant> {

        val db = DataBaseHelper(pContext)
        return db.viewParticipant()
    }

    fun dbAddParticipant(pParticipant: Participant, pContext: Context): Long {

        val id = pParticipant.getID()
        val orderPins = pParticipant.getPinSetsAsString()
        val orderInterfaces = pParticipant.getInterfacesAsString()

        val db = DataBaseHelper(pContext)
        val participant = ModelClassParticipant(id, 0, orderPins, orderInterfaces)

        val success = db.addParticipant(participant)
        Log.e("db.add_participant", success.toString())
        return success
    }

    fun dbUpdateParticipantComplete(pId: Int, pComplete: Int, pContext: Context): Int {

        val dataBaseHelper = DataBaseHelper(pContext)

        val success = dataBaseHelper.updateParticipantComplete(ModelClassParticipant(pId, pComplete))
        Log.e("db.update_participant", success.toString())
        return success
    }
}
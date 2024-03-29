package com.example.pininterface.database.interfaces

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.modelclass.ModelClassOrderInterfaces
import com.example.pininterface.database.modelclass.ModelClassOrderPins
import com.example.pininterface.database.modelclass.ModelClassParticipant
import com.example.pininterface.logic.Participant

/**
 * Interface for DB participant actions
 */
interface InterfaceDbParticipant : InterfaceDbOrderInterfaces, InterfaceDbOrderPins {

    /**
     * Gets the entire participant table
     * @param pContext context ("this" in activity)
     * @return participant table as MutableList<ModelClassParticipant>
     */
    fun dbViewListParticipant(pContext: Context): MutableList<ModelClassParticipant> {

        val db = DataBaseHelper(pContext)
        return db.viewParticipant()
    }

    /**
     * Add a new row to participant table of DB
     * @param pParticipant participant as Participant
     * @param pContext context ("this" in activity)
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun dbAddParticipant(pParticipant: Participant, pContext: Context): Long {

        val phoneID = pParticipant.getPhoneID()
        val id = pParticipant.getID()
        val orderPinsString = pParticipant.getPinSetsAsString()
        val orderPinsId = dbGetIdOrderPins(ModelClassOrderPins(-1,orderPinsString), pContext)

        val orderInterfacesString = pParticipant.getInterfacesAsString()
        val orderInterfacesId = dbGetIdOrderInterfaces(ModelClassOrderInterfaces(-1, orderInterfacesString), pContext)

        val db = DataBaseHelper(pContext)
        val participant = ModelClassParticipant(phoneID, id, 0, orderPinsId, orderInterfacesId)

        val success = db.addParticipant(participant)
        Log.e("db.add_participant", success.toString())
        return success
    }

    /**
     * Updates the complete field for one entry in the participant table
     * @param pId id of the row/entry as Int
     * @param pComplete complete status of the row/entry (0=false, 1=true) as int
     * @param pContext context ("this" in activity)
     * @return positive Int when successful, -1 if unsuccessful
     */
    fun dbUpdateParticipantComplete(pPhoneID: Int, pId: Int, pComplete: Int, pContext: Context): Int {

        val dataBaseHelper = DataBaseHelper(pContext)

        val success = dataBaseHelper.updateParticipantComplete(ModelClassParticipant(pPhoneID, pId, pComplete))
        Log.e("db.update_participant", success.toString())
        return success
    }

    /**
     * Counts the number of Rows of Table Participants
     * @param pContext Context (this)
     * @return number of rows of Participant
     */
    fun countRowsParticipant(pContext: Context): Int {

        val dataBaseHelper = DataBaseHelper(pContext)
        return dataBaseHelper.countRowsParticipant()
    }
}
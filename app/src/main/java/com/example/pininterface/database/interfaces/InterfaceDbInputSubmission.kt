package com.example.pininterface.database.interfaces

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.modelclass.ModelClassInterActionSubmission
import com.example.pininterface.enums.EnumButtonTypes
import com.example.pininterface.enums.EnumInterfaceTypes

/**
 * Interface for DB submission actions
 */
interface InterfaceDbInputSubmission {

    /**
     * Adds a new row to submission table of DB
     * @param pId id
     * @param pInterface interface typ
     * @param pPin pin
     * @param pSubmission submission/button typ
     * @param pTime time since last submission
     * @param pContext context ("this" in activity)
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun dbAddSubmission(pPhoneID: Int, pId: Int, pInterface: EnumInterfaceTypes, pPin: String, pSubmission: EnumButtonTypes, pTime: Int, pContext: Context): Long {

        val dataBaseHelper = DataBaseHelper(pContext)

        val submission = ModelClassInterActionSubmission(pPhoneID, pId, pInterface.toString(), pPin, pSubmission.toString(), pTime)

        val success = dataBaseHelper.addSubmission(submission)
        Log.e("db.add_submission", success.toString())
        return success
    }
}
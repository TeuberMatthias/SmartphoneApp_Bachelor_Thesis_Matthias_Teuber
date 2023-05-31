package com.example.pininterface.database.interfaces

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.modelclass.ModelClassFeedBack

/**
 * Interface for DB feedback actions
 */
interface InterfaceDbFeedback {

    /**
     * Add a new row to feedback table of DB
     * @param pId id
     * @param pFeedback feedback as String
     * @param pContext context ("this" in activity)
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun dbAddFeedback(pPhoneID: Int, pId: Int, pFeedback: String, pContext: Context): Long {

        val dataBaseHelper = DataBaseHelper(pContext)

        val success = dataBaseHelper.addFeedBack(ModelClassFeedBack(pPhoneID, pId, pFeedback))
        Log.e("db.add_feedback", success.toString())
        return success
    }
}
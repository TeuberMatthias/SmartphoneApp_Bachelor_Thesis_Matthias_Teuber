package com.example.pininterface.database.interfaces

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.modelclass.ModelClassDemographics

/**
 * Interface for DB demographics actions
 */
interface InterfaceDbDemographics {

    /**
     * Add a new row to demographics table of DB
     * @param pId id
     * @param pAge age
     * @param pGender gender
     * @param pDominantHand dominant hand
     * @param pContext context ("this" in activity)
     * @return positive Long if successful, -1 when unsuccessful
     */
    fun dbAddDemographics(pPhoneID: Int, pId: Int, pAge: Int, pGender: String, pDominantHand: String, pHandUsed: String, pHandUsedNormally: String, pContext: Context): Long {

        val dataBaseHelper = DataBaseHelper(pContext)

        val success = dataBaseHelper.addDemographics(ModelClassDemographics(pPhoneID, pId, pAge, pGender, pDominantHand, pHandUsed, pHandUsedNormally))
        Log.e("db.add_demographics", success.toString())
        return success
    }
}
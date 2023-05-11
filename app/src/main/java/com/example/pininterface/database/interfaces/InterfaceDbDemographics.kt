package com.example.pininterface.database.interfaces

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.modelclass.ModelClassDemographics

interface InterfaceDbDemographics {

    fun dbAddDemographics(pId: Int, pAge: Int, pGender: String, pDominantHand: String, pContext: Context): Long {

        val dataBaseHelper = DataBaseHelper(pContext)

        val success = dataBaseHelper.addDemographics(ModelClassDemographics(pId, pAge, pGender, pDominantHand))
        Log.e("db.add_demographics", success.toString())
        return success
    }
}
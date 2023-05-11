package com.example.pininterface.database.interfaces

import android.content.Context
import android.util.Log
import com.example.pininterface.database.DataBaseHelper
import com.example.pininterface.database.modelclass.ModelClassInterActionSubmission
import com.example.pininterface.enums.EnumButtonTypes
import com.example.pininterface.enums.EnumInterfaceTypes

interface InterfaceDbInputSubmission {

    fun dbAddSubmission(pId: Int, pInterface: EnumInterfaceTypes, pPin: String, pSubmission: EnumButtonTypes, pTime: Int, pContext: Context): Long {

        val dataBaseHelper = DataBaseHelper(pContext)

        val submission = ModelClassInterActionSubmission(pId, pInterface.toString(), pPin, pSubmission.toString(), pTime)

        val success = dataBaseHelper.addSubmission(submission)
        Log.e("db.add_submission", success.toString())
        return success
    }
}